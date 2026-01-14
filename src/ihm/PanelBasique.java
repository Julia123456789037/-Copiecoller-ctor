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

	private JTextField txtFld1;
	private JTextField txtFld2;


	private ButtonGroup btnGroup;

	private JPanel pnlCentre;

	public PanelBasique()
	{

		this.setLayout(new BorderLayout());
		/*-------------------------------*/
		/* Création des Composants */
		/*-------------------------------*/

		this.pnlCentre = new JPanel(new GridLayout(1, 2, 10, 10));

		this.bouton = new JButton("Comparer");
		this.sb = new JScrollBar(JScrollBar.HORIZONTAL, 0, 40, 0, 150 + 40); // 1

		this.sb.setBlockIncrement(32);
		this.sb.setUnitIncrement(16);

		this.txtFld1 = new JTextField();

		this.txtFld2 = new JTextField();

		this.btnGroup = new ButtonGroup();

		/*-------------------------------*/
		/* Positionnment des Composant */
		/*-------------------------------*/

		this.pnlCentre.add(this.txtFld1);
		this.pnlCentre.add(this.txtFld2);

		this.add(this.pnlCentre, BorderLayout.CENTER);
		this.add(this.bouton, BorderLayout.SOUTH);

		/*-------------------------------*/
		/* Activation des Composants */
		/*-------------------------------*/

		this.sb.addAdjustmentListener(this);

		this.setVisible(true);

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
			case 3 -> sMess += "Bloc gauche "  ;
			case 4 -> sMess += "Bloc droite "  ;
			case 5 -> sMess += "Schroll bar "  ;
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

	}

}
