/**
 * 
 */
package com.health.gui.output;

import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

import java.awt.Component;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JViewport;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.health.Column;
import com.health.Record;
import com.health.Table;
import com.health.ValueType;

/**
 * @author Bjorn van der Laan
 *
 */
public class OutputMainPanelTest {
    private OutputMainPanel panel;

    @Before
    public void setUp() {
    }

    @Test
    public void testSetJTableData() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        JTable component = new JTable();
        map.put("testJTable", component);

        OutputMainPanel.setData(map);
        int expected = OutputMainPanel.getPane().getComponentCount();
        assertEquals(expected, 1);

        JScrollPane scrollPane = (JScrollPane) OutputMainPanel.getPane()
                .getComponent(0);
        JViewport port = (JViewport) scrollPane.getComponent(0);
        JTable actual = (JTable) port.getView();
        assertEquals(component, actual);
    }

    @Test
    public void testSetComponentData() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Component component = Mockito.mock(Component.class);
        map.put("testComponent", component);

        OutputMainPanel.setData(map);
        int expected = OutputMainPanel.getPane().getComponentCount();
        assertEquals(expected, 1);

        Component actual = OutputMainPanel.getPane().getComponent(0);
        assertEquals(component, actual);
    }

    // not working yet. Is same object, but some properties change when inserted
    // into output section.
    @Test
    public void testSetTableData() throws Exception {
        HashMap<String, Object> map = new HashMap<String, Object>();
        Table component = createTable();
        map.put("testTable", component);

        OutputMainPanel.setData(map);
        int expected = OutputMainPanel.getPane().getComponentCount();
        assertEquals(expected, 1);

        JScrollPane scrollPane = (JScrollPane) OutputMainPanel.getPane()
                .getComponent(0);
        JViewport port = (JViewport) scrollPane.getComponent(0);
        JTable actual = (JTable) port.getView();

        assertEquals(component, actual);
    }

    private Table createTable() {
        Column[] columns = new Column[] {
                new Column("abc", 0, ValueType.Number),
                new Column("column2", 1, ValueType.Number),
                new Column("cda", 2, ValueType.String),
                new Column("column4", 3, ValueType.Date), };
        return new Table(Arrays.asList(columns));
    }
}
