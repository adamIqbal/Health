package com.health.control;

import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.health.EventList;
import com.health.EventSequence;
import com.health.Table;
import com.health.input.Input;
import com.health.input.InputException;
import com.health.interpreter.Interpreter;
import com.health.operations.Code;
import com.health.output.Output;
import com.health.script.runtime.Context;
import com.health.script.runtime.NumberValue;
import com.health.script.runtime.StringValue;
import com.health.script.runtime.WrapperValue;
import com.health.visuals.BoxPlot;
import com.health.visuals.FreqBar;
import com.health.visuals.Histogram;
import com.health.visuals.StateTransitionMatrix;

/**
 *
 */
public final class ControlModule {
    private String script;
    private List<InputData> data;

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
            Output.writeTable(((StringValue) args[0]).getValue(), ((WrapperValue<Table>) args[1]).getValue());
            return null;
        });

        context.declareStaticMethod("writeFormatted", (args) -> {
            Output.writeTable(
                    ((StringValue) args[0]).getValue(),
                    ((WrapperValue<Table>) args[1]).getValue(),
                    ((StringValue) args[2]).getValue());
            return null;
        });

        context.declareStaticMethod("freqbar", (args) -> {
            if (args.length >= 2) {
                FreqBar.frequencyBar(
                        ((WrapperValue<Table>) args[0]).getValue(),
                        ((StringValue) args[1]).getValue());
            } else {
                FreqBar.frequencyBar(((WrapperValue<Table>) args[0]).getValue());
            }
            return null;
        });

        context.declareStaticMethod("boxplot", (args) -> {
            BoxPlot.boxPlot(((WrapperValue<Table>) args[0]).getValue(), ((StringValue) args[1]).getValue());
            return null;
        });

        context.declareStaticMethod("hist", (args) -> {
            Histogram.createHistogram(
                    ((WrapperValue<Table>) args[0]).getValue(),
                    ((StringValue) args[1]).getValue(),
                    (int) ((NumberValue) args[2]).getValue());
            return null;
        });

        context.declareStaticMethod("sequence", (args) -> {
            String[] sequence = new String[args.length];

            for (int i = 0; i < args.length; i++) {
                sequence[i] = ((StringValue) args[i]).getValue();
            }

            return new WrapperValue<EventSequence>(new EventSequence(sequence));
        });

        context.declareStaticMethod("findSequences", (args) -> {
            EventList events = ((WrapperValue<EventList>) args[0]).getValue();
            EventSequence sequence = ((WrapperValue<EventSequence>) args[1]).getValue();

            Code.fillEventSequence(sequence, events);

            return new WrapperValue<List<EventList>>(sequence.getSequences());
        });

        context.declareStaticMethod("transitionMatrix", (args) -> {
            if (args.length >= 2) {
                StateTransitionMatrix.createStateTrans(
                        ((WrapperValue<EventList>) args[0]).getValue(),
                        ((WrapperValue<List<EventList>>) args[1]).getValue());
            } else {
                StateTransitionMatrix.createStateTrans(
                        ((WrapperValue<EventList>) args[0]).getValue());
            }
            return null;
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
