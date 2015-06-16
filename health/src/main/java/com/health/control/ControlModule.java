package com.health.control;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.EventList;
import com.health.EventSequence;
import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;
import com.health.interpreter.Interpreter;
import com.health.operations.Code;
import com.health.operations.TableWithDays;
import com.health.output.Output;
import com.health.script.runtime.BooleanValue;
import com.health.script.runtime.Context;
import com.health.script.runtime.EventListValue;
import com.health.script.runtime.EventSequenceValue;
import com.health.script.runtime.NumberValue;
import com.health.script.runtime.StringValue;
import com.health.script.runtime.WrapperValue;
import com.health.visuals.BoxPlot;
import com.health.visuals.FreqBar;
import com.health.visuals.Histogram;
import com.health.visuals.StateTransitionMatrix;
import com.xeiam.xchart.Chart;

/**
 *
 */
public final class ControlModule {
    private String script;
    private List<InputData> data;
    private Map<String, Object> output = new HashMap<String, Object>();
    int numBoxPlots, numFreqBars, numTransitionMatrices, numHistograms;

    /**
     * Start analysis based on the script.
     *
     * @return nothing.
     * @throws IOException
     *             if any I/O errors occur.
     */
    public Context startAnalysis() throws IOException {
        if (this.script == null) {
            throw new IllegalStateException(
                    "Script cannot be null. Set the script to a String isntance using setScript(String).");
        }

        Context context = this.createContext();

        this.loadAllData(context);

        Interpreter.interpret(this.script, context);

        return context;
    }

    public Map<String, Object> getOutput() {
        return Collections.unmodifiableMap(this.output);
    }

    /**
     * Gets the data of this control module.
     *
     * @return the data of this control module.
     */
    public List<InputData> getData() {
        return data;
    }

    /**
     * Sets the data of this control module.
     *
     * @param data
     *            the data of this control module.
     */
    public void setData(final List<InputData> data) {
        this.data = data;
    }

    /**
     * Gets the script of this control module.
     *
     * @return the script of this control module.
     */
    public String getScript() {
        return script;
    }

    /**
     * Sets the script of this control module.
     *
     * @param script
     *            the script of this control module.
     */
    public void setScript(final String script) {
        this.script = script;
    }

    @Override
    public String toString() {
        return "ControlModule [data=" + data.toString() + ", script=" + script + "]";
    }

    private Context createContext() {
        Context context = new Context();

        context.declareStaticMethod("println", (args) -> {
            System.out.println(((StringValue) args[0]).getValue());
            return null;
        });

        context.declareStaticMethod("write", (args) -> {
            String filename = ((StringValue) args[0]).getValue();
            Table table = ((WrapperValue<Table>) args[1]).getValue();

            if (args.length >= 3) {
                Output.writeTable(
                        filename,
                        table,
                        ((StringValue) args[2]).getValue());
            } else {
                Output.writeTable(filename, table);
            }

            this.output.put(new File(filename).getName(), table);
            return null;
        });

        context.declareStaticMethod("freqbar", (args) -> {
            Chart chart;

            String filename = null;
            int baseIndex = 0;

            if (args[0] instanceof StringValue) {
                filename = ((StringValue) args[0]).getValue();
                baseIndex++;
            }

            if (args.length >= baseIndex + 2) {
                chart = FreqBar.frequencyBar(
                        ((WrapperValue<Table>) args[baseIndex + 0]).getValue(),
                        ((StringValue) args[baseIndex + 1]).getValue());
            } else {
                chart = FreqBar.frequencyBar(((WrapperValue<Table>) args[baseIndex + 0]).getValue());
            }

            if (filename != null) {
                FreqBar.saveGraph(chart, filename);
            }

            this.output.put("freqbar" + ++numFreqBars, FreqBar.getContainer(chart));
            return null;
        });

        context.declareStaticMethod("boxplot", (args) -> {
            JPanel panel;

            if (args.length >= 2) {
                panel = BoxPlot.boxPlot(((WrapperValue<Table>) args[0]).getValue(),
                        ((StringValue) args[1]).getValue());
            } else {
                panel = BoxPlot.boxPlot(((WrapperValue<Table>) args[0]).getValue());
            }

            this.output.put("boxplot" + ++numBoxPlots, panel);
            return null;
        });

        context.declareStaticMethod("hist", (args) -> {
            JPanel panel = Histogram.createHistogram(
                    ((WrapperValue<Table>) args[0]).getValue(),
                    ((StringValue) args[1]).getValue(),
                    (int) ((NumberValue) args[2]).getValue());

            this.output.put("histogram" + ++numHistograms, panel);
            return null;
        });

        context.declareStaticMethod("sequence", (args) -> {
            String[] sequence;
            boolean connected = true;

            if (args.length > 0 && args[args.length - 1] instanceof BooleanValue) {
                connected = ((BooleanValue) args[args.length - 1]).getValue();

                sequence = new String[args.length - 1];

                for (int i = 0; i < args.length - 1; i++) {
                    sequence[i] = ((StringValue) args[i]).getValue();
                }
            } else {
                sequence = new String[args.length];

                for (int i = 0; i < args.length; i++) {
                    sequence[i] = ((StringValue) args[i]).getValue();
                }
            }

            return new EventSequenceValue(new EventSequence(sequence, connected));
        });

        context.declareStaticMethod("findSequences", (args) -> {
            EventList events = ((EventListValue) args[0]).getValue();
            EventSequence sequence = ((EventSequenceValue) args[1]).getValue();

            Code.fillEventSequence(sequence, events);

            return new EventSequenceValue(sequence);
        });

        context.declareStaticMethod(
                "transitionMatrix",
                (args) -> {

                    String filename = null;
                    int baseIndex = 0;

                    if (args[0] instanceof StringValue) {
                        filename = ((StringValue) args[0]).getValue();
                        baseIndex++;
                    }

                    EventList codes = ((EventListValue) args[baseIndex + 0]).getValue();
                    JTable table;

                    if (args.length >= baseIndex + 2) {
                        EventSequence sequence = ((EventSequenceValue) args[baseIndex + 1]).getValue();

                        table = StateTransitionMatrix.createStateTrans(codes,
                                Code.fillEventSequence(sequence, codes));
                    } else {
                        table = StateTransitionMatrix.createStateTrans(codes);
                    }

                    if (filename != null) {
                        StateTransitionMatrix.saveFile(filename, table);
                    }

                    this.output.put("transitionMatrix" + ++numBoxPlots, table);
                    return null;
                });

        context.declareStaticMethod("tableWithDays", (args) -> {
            return new WrapperValue<Table>(TableWithDays.TableDays(((WrapperValue<Table>) args[0]).getValue()));
        });

        return context;
    }

    private void loadAllData(final Context context) {
        if (this.data != null) {
            for (int i = 0; i < this.data.size(); i++) {
                this.loadData(this.data.get(i), context);
            }
        }
    }

    private void loadData(final InputData input, final Context context) {
        Table table = null;

        try {
            table = Input.readTable(input.getFilePath(), input.getConfigPath());
        } catch (IOException | ParserConfigurationException
                | SAXException | InputException e) {
            System.out.println("Error: Something went wrong parsing the config and data!");

            e.printStackTrace();

            return;
        }

        WrapperValue<Table> value = new WrapperValue<Table>(table);

        context.declareLocal(input.getName(), value.getType(), value);
    }
}
