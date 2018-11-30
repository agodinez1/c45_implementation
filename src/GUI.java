import classifier.C45;
import data.Data;
import types.Instance;
import util.ConsoleOutputCapturer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.LinkedHashMap;


/**
 * @Author Andre Godinez
 * <p>
 * GUI for C4.5 Decision Tree classifier
 */
public class GUI {
    private JPanel panel1;
    private JTextField attributeName;
    private JCheckBox yesCheckBox;
    private JCheckBox noCheckBox;
    private JCheckBox realCheckBox;
    private JCheckBox discreteCheckBox;
    private JButton addAttribute;
    private JTextPane logPane;
    private JTextField possibleTargetValues;
    private JButton finishButton;
    private JButton defaultAssignmentValuesButton;
    private JTextField fileNameInput;
    private JButton loadDataButton;
    private JTextField minimumInstances;
    private JTextField maxDepth;
    private JButton trainClassifierButton;
    private JButton crossValidationAccuracyButton;
    private JButton printDecisionTreeButton;
    private JButton createClassifierButton;
    private JButton defaultDataButton;
    private JTextField predictionAttributes;
    private JButton predictButton;
    private JLabel attributeLabels;
    private JButton restartButton;

    // List of default attributes for assignment
    private String[] defaultAssignmentValues = {"body-length real n", "wing-length real n", "body-width real n", "wing-width real n",
            "type [BarnOwl,SnowyOwl,LongEaredOwl] target"};

    // variables to hold inputted attributes
    private ArrayList<String> attrList = new ArrayList<>();
    private String[] attrArray;

    // log string to put in jscroll pane
    private String log = "";

    //classifier variables
    Data data;
    C45 classifier;

    //output capturer to capture system.out.printn() outputs and to put in jscroll pane
    ConsoleOutputCapturer outputCapturer = new ConsoleOutputCapturer();

    private void updateLog() {
        logPane.setText(log);
    }

    private void disableAttributeForm() {
        finishButton.setEnabled(false);
        defaultAssignmentValuesButton.setEnabled(false);
        yesCheckBox.setEnabled(false);
        noCheckBox.setEnabled(false);
        realCheckBox.setEnabled(false);
        discreteCheckBox.setEnabled(false);
        possibleTargetValues.setEnabled(false);
        attributeName.setEnabled(false);
        addAttribute.setEnabled(false);
    }


    private void enableAttributeForm() {
        finishButton.setEnabled(true);
        defaultAssignmentValuesButton.setEnabled(true);
        yesCheckBox.setEnabled(true);
        noCheckBox.setEnabled(true);
        realCheckBox.setEnabled(true);
        discreteCheckBox.setEnabled(true);
        possibleTargetValues.setEnabled(true);
        attributeName.setEnabled(true);
        addAttribute.setEnabled(true);
    }


    private void enableFileInput() {
        fileNameInput.setEnabled(true);
        loadDataButton.setEnabled(true);
        defaultDataButton.setEnabled(true);
    }

    private void disableFileInput() {
        fileNameInput.setEnabled(false);
        loadDataButton.setEnabled(false);
        defaultDataButton.setEnabled(false);
    }

    private void enableClassifierSettings() {
        createClassifierButton.setEnabled(true);
        minimumInstances.setEnabled(true);
        maxDepth.setEnabled(true);
    }

    private void disableClassifierSettings() {
        createClassifierButton.setEnabled(false);
        minimumInstances.setEnabled(false);
        maxDepth.setEnabled(false);
    }

    private void enableClassifierFunctions() {
        printDecisionTreeButton.setEnabled(true);
        crossValidationAccuracyButton.setEnabled(true);
    }

    private void disabledClassifierFunctions() {
        printDecisionTreeButton.setEnabled(false);
        crossValidationAccuracyButton.setEnabled(false);
    }

    private void enablePredictFunctions() {
        predictButton.setEnabled(true);
        predictionAttributes.setEnabled(true);
    }

    private void disablePredictFunctions() {
        predictButton.setEnabled(false);
        predictionAttributes.setEnabled(false);
    }

    // populate the attribute labels with this text
    private void populatePredictionAttributes() {
        String superStr = "";

        for (int i = 0; i < attrList.size() - 1; i++) {
            if (i == attrList.size() - 2) {
                superStr += attrList.get(i);
            } else {
                superStr += attrList.get(i) + ",";
            }
        }

        attributeLabels.setText(superStr);
    }

    private void clearPredictionAttributes() {
        attributeLabels.setText("- -");
    }

    public GUI() {

        /**
         * Add attribute button
         *
         * Takes the values from
         *
         * attributeName input
         * isTarget input
         * possibleValues input
         *
         * adds it to the attributeList variables
         *
         */
        addAttribute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = attributeName.getText();
                String isTarget = yesCheckBox.isSelected() ? "target" : "n";
                String possibleValues = realCheckBox.isSelected() ? "real" : possibleTargetValues.getText();

                String attribute = name + " " + possibleValues + " " + isTarget;

                System.out.println(attribute);
                attrList.add(attribute);

                String superStr = "";
                int i = 0;
                for (String attr : attrList) {
                    superStr += "Attribute " + (i + 1) + " { " + attr + " }" + "\n";
                    i++;
                }

                log += superStr;
                updateLog();
                finishButton.setEnabled(true);
            }
        });


        /**
         * Finish Button
         *
         * Converts all the attributes in attribute list to a readable string array
         * by the C4.5 classifier
         *
         */
        finishButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attrArray = new String[attrList.size()];
                String superStr = "";
                for (int i = 0; i < attrList.size(); i++) {
                    attrArray[i] = attrList.get(i);
                }
                loadDataButton.setEnabled(true);
                disableAttributeForm();
            }
        });

        /**
         * Default assignment values button
         *
         * Loads the default attribute values for this assignment
         *
         */
        defaultAssignmentValuesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                attrArray = defaultAssignmentValues;

                String superStr = "";
                for (int i = 0; i < attrArray.length; i++) {
                    String attrName = attrArray[i].split(" ")[0];
                    attrList.add(attrName);

                    superStr += "Attribute " + (i + 1) + " { " + attrArray[i] + " }" + "\n";
                }

                log += superStr;
                logPane.setText(log);
                loadDataButton.setEnabled(true);
                disableAttributeForm();
                enableFileInput();
            }
        });


        /**
         * Load data button
         *
         * gets the file name from fileNameInput then creates a Data object with the fileName
         *
         */
        loadDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = fileNameInput.getText();
                try {
                    data = new Data(attrArray, fileName);

                    String success = fileName + "Data loaded...\n";
                    log += success;

                    disableFileInput();
                    enableClassifierSettings();
                    updateLog();
                } catch (IOException e1) {
                    String error = e1.getMessage() + "\n";
                    log += error;

                    updateLog();
                }
            }
        });

        /**
         * Default Data button
         *
         * Loads the default data fileName for this assignment
         *
         */
        defaultDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fileName = "owls.csv";
                fileNameInput.setText(fileName);
                try {
                    data = new Data(attrArray, fileName);

                    String success = fileName + " Data loaded...\n";
                    log += success;

                    disableFileInput();
                    enableClassifierSettings();
                    updateLog();
                } catch (IOException e1) {
                    String error = e1.getMessage() + "\n";
                    log += error;

                    updateLog();
                }
            }
        });

        /**
         * Create Classifier button
         *
         * Gets the values from minimumInstances input and maxDepth input
         * and creates a classifier with those inputs
         *
         */
        createClassifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int minInstance = Integer.parseInt(minimumInstances.getText());
                int max_depth = Integer.parseInt(maxDepth.getText());
                classifier = new C45(minInstance, max_depth);

                log += "Classifier created...\n";

                updateLog();
                disableClassifierSettings();
                trainClassifierButton.setEnabled(true);
            }
        });

        /**
         * Train classifier button
         *
         * Trains the created classifier with the inputted Data object
         *
         */
        trainClassifierButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                classifier.train(data);

                log += "Classifier trained...\n";
                updateLog();
                trainClassifierButton.setText("Train Classifier");

                trainClassifierButton.setEnabled(false);
                enableClassifierFunctions();
                enablePredictFunctions();
                populatePredictionAttributes();
            }
        });

        /**
         * Print decision tree button
         *
         * Prints the trained classifier decision tree
         *
         */
        printDecisionTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                outputCapturer.start();

                classifier.printDecisionTree();

                log += outputCapturer.stop() + "\n END DECISION TREE\n\n";
                updateLog();
            }
        });

        /**
         * Cross validation accuracy button
         *
         * Gets average accuracy of the classifier using cross validation
         *
         */
        crossValidationAccuracyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    outputCapturer.start();

                    classifier.crossValidation(data, 10);

                    log += outputCapturer.stop() + "\n END CROSS VALIDATION\n\n";
                    updateLog();
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }
            }
        });


        /**
         * Predict button
         *
         * Predicts the target value based on the predictionAttributes
         *
         */
        predictButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String[] attrAtt = predictionAttributes.getText().split(",");

                if(attrAtt.length < attrList.size() - 1) {
                    log+="Invalid input... \n";

                    updateLog();
                } else {
                    LinkedHashMap<String, String> attributeValuePairs = new LinkedHashMap<>();

                    for (int i = 0; i < attrList.size() - 1; i++) {
                        attributeValuePairs.put(attrList.get(i), attrAtt[i]);
                    }

                    Instance instance = new Instance(attributeValuePairs, "");

                    String predicted = classifier.predict(instance, classifier.decisionTree);


                    log += "Predicted value: " + predicted + "\n";

                    populatePredictionAttributes();
                    updateLog();
                }
            }
        });

        restartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableAttributeForm();
                disableClassifierSettings();
                disabledClassifierFunctions();
                disablePredictFunctions();
                clearPredictionAttributes();
                attrList.clear();
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setSize(600, 1000);
        frame.setContentPane(new GUI().panel1);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
