package com.group15.AutomatedHydroponicsSystem.mqtt.dto;

import com.amazonaws.services.iot.client.AWSIotException;
import com.amazonaws.services.iot.client.AWSIotMessage;
import com.amazonaws.services.iot.client.AWSIotQos;
import com.amazonaws.services.iot.client.AWSIotTopic;
import com.group15.AutomatedHydroponicsSystem.mqtt.DataVariables;
import com.group15.AutomatedHydroponicsSystem.mqtt.JsonKeys;
import com.group15.AutomatedHydroponicsSystem.mqtt.Topics;
import com.group15.AutomatedHydroponicsSystem.mqtt.service.MqttService;
import com.group15.AutomatedHydroponicsSystem.mqtt.utils.MqttConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.json.JSONObject;

public class MyTopic extends AWSIotTopic {
    @Autowired
    private MqttService service;

    @Autowired
    private MqttConfig mqttConfig;

    public MyTopic(String topic, AWSIotQos qos) {
        super(topic, qos);
    }

    @Override
    public void onMessage(AWSIotMessage message) {
        // called when a message is received
        String receivedData = new String(message.getPayload());
        String receivedTopic = message.getTopic();
        System.out.println("The data received is: " + receivedData + " from the topic: " + receivedTopic);
        JSONObject jsonData = new JSONObject(receivedData);

        switch (receivedTopic) {
            case Topics.phsensorTopic -> {
                DataVariables.pHData = jsonData.getDouble(JsonKeys.pHKey);
                System.out.println("PH value : " + DataVariables.pHData);
            }
            case Topics.phlowpumpTopic -> {
                DataVariables.phlowpumpData = jsonData.getBoolean(JsonKeys.acidicPumpStateKey);
                System.out.println("PH pump state : " + DataVariables.phlowpumpData);
                if (DataVariables.phlowpumpData) {
                    DataVariables.phLowPumpCount++;
                    System.out.println("The acid pump is on"); // Display it on the page
                } else {
                    System.out.println("The acid pump is off");
                }

                if(DataVariables.phLowPumpCount == 30){
                    System.out.println("Acidic liquid container is going to empty! REFILL IT.");
                } else if (DataVariables.phLowPumpCount == 40) {
                    System.out.println("ALERT!!! Acidic liquid container is emptied"); // reset the count to 0 by clicking a button after refilling it
                    System.out.println("The acidic liquid pump is OFF");

                    try {
                        service.publishMessageToTopic(false,Topics.pHLowPumpBack); // To save the pump from air drying
                    } catch (AWSIotException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            case Topics.phpumpTopic -> {
                DataVariables.phpumpData = jsonData.getBoolean(JsonKeys.basePumpStateKey);
                if (DataVariables.phpumpData) {
                    DataVariables.phPumpCount++;
                    System.out.println("The count : " + DataVariables.phPumpCount);
                    System.out.println("The base pump is on"); // Display it on the page
                } else {
                    System.out.println("The base pump is off");
                }

                if(DataVariables.phPumpCount == 30){
                    System.out.println("Base liquid container is going to empty! REFILL IT.");
                } else if (DataVariables.phPumpCount == 40) {
                    System.out.println("ALERT!!! Base liquid container is emptied"); // reset the count to 0 by clicking a button after refilling it
                    System.out.println("The base liquid pump is OFF");

                    try {
                        service.publishMessageToTopic(0,Topics.pHHighPumpBack); // To save the pump from air drying
                    } catch (AWSIotException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }

                }
            }

            // Not possible 99.99%
            case Topics.phhigherrorTopic -> {
                DataVariables.phhigherrorData = jsonData.getBoolean(JsonKeys.pHHighErrorKey);
            }

            // Not possible 99.99%
            case Topics.phlowerrorTopic -> {
                DataVariables.phlowerrorData = jsonData.getBoolean(JsonKeys.pHLowErrorKey);
            }
            case Topics.phsensorerrorTopic -> {
                DataVariables.phsensorerrorData = jsonData.getBoolean(JsonKeys.pHSensorErrorKey);
                if (DataVariables.phsensorerrorData) {
                    mqttConfig.publishData(0,Topics.phlowpumpTopic);
                    mqttConfig.publishData(0,Topics.phpumpTopic);
                    System.out.println("The ph sensor is not working properly."); // Alert the user
                }
            }

            case Topics.tdssensorTopic -> {
                DataVariables.tdsData = jsonData.getDouble(JsonKeys.tdsKey);
            }
            case Topics.tdspumpTopic -> {
                DataVariables.tdspumpData = jsonData.getBoolean(JsonKeys.tdsPumpStateKey);
                if (DataVariables.tdspumpData) {
                    DataVariables.tdsPumpCount++;
                    System.out.println("The tds pump is on"); // Display it on the page
                } else {
                    System.out.println("The tds pump is off");
                }

                if(DataVariables.tdsPumpCount == 30){
                    System.out.println("Nutrient container is going to empty! REFILL IT.");
                } else if (DataVariables.tdsPumpCount == 40) {
                    System.out.println("ALERT!!! Nutrient container is emptied"); // reset the count to 0 by clicking a button after refilling it
                    System.out.println("The tds pump is OFF");

                    try {
                        service.publishMessageToTopic(0,Topics.tdsPumpBack); // To save the pump from air drying
                    } catch (AWSIotException | InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            case Topics.tdserrorTopic -> {
                DataVariables.tdserrorData = jsonData.getBoolean(JsonKeys.tdsErrorKey);
            }
            case Topics.tdssensorerrorTopic -> {
                DataVariables.tdssensorerrorData = jsonData.getBoolean(JsonKeys.tdsSensorErrorKey);
                if (DataVariables.tdssensorerrorData) {
                    System.out.println("The tds sensor is not working properly."); // Alert the user
                    mqttConfig.publishData(0,Topics.tdspumpTopic);
                }
            }
            case Topics.floatsensorTopic -> {
                DataVariables.floatSensorData = jsonData.getBoolean(JsonKeys.floatKey);
            }

            default -> System.out.println("Received data not relevant to this device.");
        }

    }

 }
