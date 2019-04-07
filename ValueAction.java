import javax.swing.AbstractAction;

public abstract class ValueAction extends AbstractAction
{
    final int value;
    ValueAction(int v)
    {
        super();
        value=v;
    }
}
