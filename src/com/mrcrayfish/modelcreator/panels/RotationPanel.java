package com.mrcrayfish.modelcreator.panels;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.Hashtable;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;

import com.mrcrayfish.modelcreator.Cube;
import com.mrcrayfish.modelcreator.IValueUpdater;
import com.mrcrayfish.modelcreator.ModelCreator;

public class RotationPanel extends JPanel implements IValueUpdater
{
	private static final long serialVersionUID = 1L;

	private ModelCreator creator;

	private OriginPanel panelOrigin;
	private JPanel axisPanel;
	private JComboBox<String> axisList;
	private JPanel sliderPanel;
	private JSlider rotation;
	private JPanel extraPanel;
	private JRadioButton btnRescale;

	private final int ROTATION_MIN = 0;
	private final int ROTATION_MAX = 15;
	private final int ROTATION_INIT = 0;

	public RotationPanel(ModelCreator creator)
	{
		this.creator = creator;
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		initComponents();
		addComponents();
	}

	public void initComponents()
	{
		panelOrigin = new OriginPanel(creator);
		panelOrigin.setMaximumSize(new Dimension(186, 50));

		axisPanel = new JPanel(new GridLayout(1, 1));
		axisPanel.setBorder(BorderFactory.createTitledBorder("Axis"));
		axisList = new JComboBox<String>();
		axisList.addItem("X");
		axisList.addItem("Y");
		axisList.addItem("Z");
		axisList.setToolTipText("The axis the element will rotate around");
		axisList.addActionListener(e ->{
			creator.getSelectedCube().setPrevAxis(axisList.getSelectedIndex());
		});
		axisPanel.setMaximumSize(new Dimension(186, 500));
		axisPanel.add(axisList);

		Hashtable<Integer, JLabel> labelTable = new Hashtable<Integer, JLabel>();
		labelTable.put(new Integer(0), new JLabel("0�"));
		labelTable.put(new Integer(4), new JLabel("90�"));
		labelTable.put(new Integer(8), new JLabel("180�"));
		labelTable.put(new Integer(12), new JLabel("270�"));

		sliderPanel = new JPanel(new GridLayout(1, 1));
		sliderPanel.setBorder(BorderFactory.createTitledBorder("Rotation"));
		rotation = new JSlider(JSlider.HORIZONTAL, ROTATION_MIN, ROTATION_MAX, ROTATION_INIT);
		rotation.setMajorTickSpacing(4);
		rotation.setMinorTickSpacing(1);
		rotation.setPaintTicks(true);
		rotation.setPaintLabels(true);
		rotation.setLabelTable(labelTable);
		rotation.addChangeListener(e ->
		{
			creator.getSelectedCube().setRotation(rotation.getValue() * 22.5D);
		});
		sliderPanel.setMaximumSize(new Dimension(186, 500));
		sliderPanel.add(rotation);
		
		extraPanel = new JPanel(new GridLayout(1, 1));
		extraPanel.setBorder(BorderFactory.createTitledBorder("Extras"));
		btnRescale = new JRadioButton("Rescale");
		btnRescale.setToolTipText("<html>Should scale faces across whole block<br>Default: Off<html>");
		extraPanel.setMaximumSize(new Dimension(186, 500));
		extraPanel.add(btnRescale);
	}

	public void addComponents()
	{
		add(panelOrigin);
		add(Box.createVerticalStrut(5));
		add(axisPanel);
		add(Box.createVerticalStrut(5));
		add(sliderPanel);
		add(Box.createVerticalStrut(5));
		add(extraPanel);
	}

	@Override
	public void updateValues(Cube cube)
	{
		panelOrigin.updateValues(cube);
		axisList.setSelectedIndex(cube.getPrevAxis());
		rotation.setValue((int)(cube.getRotation() / 22.5));
	}
}
