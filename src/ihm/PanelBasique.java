package ihm;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.Color;

import javax.swing.*;

public class PanelBasique extends JPanel implements ActionListener, ItemListener, AdjustmentListener
{

	private JButton bouton;
	private JScrollBar sb;
	private JTextField txtFld;

	private ButtonGroup btnGroup;

	private JRadioButton cbChoix1;
	private JRadioButton cbChoix2;

	private JPanel pnlCentre;

	public PanelBasique()
	{

		this.setLayout(new GridLayout(4, 1));
		/*-------------------------------*/
		/* Création des Composants */
		/*-------------------------------*/

		this.pnlCentre = new JPanel();

		this.bouton = new JButton("coucou");
		this.sb = new JScrollBar(JScrollBar.HORIZONTAL, 0, 40, 0, 150 + 40); // 1

		this.sb.setBlockIncrement(32);
		this.sb.setUnitIncrement(16);

		this.btnGroup = new ButtonGroup();
		this.cbChoix1 = new JRadioButton("choix 1");
		this.cbChoix2 = new JRadioButton("choix 2");

		this.txtFld = new JTextField();
		this.txtFld.setEditable(false);
		this.txtFld.setBackground(Color.GRAY);

		/*-------------------------------*/
		/* Positionnment des Composant */
		/*-------------------------------*/

		this.add(this.bouton);
		this.add(this.txtFld);
		this.add(this.sb);

		this.btnGroup.add(this.cbChoix1);
		this.btnGroup.add(this.cbChoix2);

		this.add(this.pnlCentre);

		this.pnlCentre.add(this.cbChoix1);
		this.pnlCentre.add(this.cbChoix2);
		/*-------------------------------*/
		/* Activation des Composants */
		/*-------------------------------*/

		this.sb.addAdjustmentListener(this);

		this.setVisible(true);

		this.cbChoix1.addItemListener(this); // pour intercepter le changement
												// d'état
		this.cbChoix2.addItemListener(this); // pour intercepter le changement
												// d'état

		this.setVisible(true);
		this.pnlCentre.setVisible(true);
	}

	public void adjustmentValueChanged(AdjustmentEvent e)
	{
		String sMess = "";

		switch (e.getAdjustmentType())
		{
		case 1 -> sMess += "Bouton droite ";
		case 2 -> sMess += "Bouton gauche ";
		case 3 -> sMess += "Bloc gauche ";
		case 4 -> sMess += "Bloc droite ";
		case 5 -> sMess += "Schroll bar ";
		}

		System.out.println(e.getAdjustmentType());

		sMess += String.format("%5d", sb.getValue());

		System.out.println(sMess);
	}

	public void actionPerformed(ActionEvent e)
	{
		// if ( e.getSource() == bouton)
		// {

		// }
	}

	public void itemStateChanged(ItemEvent e)
	{
		if (e.getSource() == this.cbChoix1)
		{
			this.cbChoix1.setBackground(Color.BLUE);
			this.cbChoix2.setBackground(Color.RED);
			this.txtFld.setEditable(true);
			this.txtFld.setBackground(Color.WHITE);
		}

		if (e.getSource() == this.cbChoix2)
		{
			this.cbChoix1.setBackground(Color.RED);
			this.cbChoix2.setBackground(Color.BLUE);
			this.txtFld.setEditable(false);
			this.txtFld.setBackground(Color.DARK_GRAY);
		}

	}

}
