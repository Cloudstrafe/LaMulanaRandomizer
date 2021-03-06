package lmr.randomizer.rcd.object;

/**
 * Created by thezerothcat on 7/17/2017.
 */
public class WriteByteOperation {
    private int index;
    private int value;
    private ByteOp op;

    public WriteByteOperation() {
    }

    public WriteByteOperation(int index, ByteOp op, int value) {
        this.index = index;
        this.op = op;
        this.value = value;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public ByteOp getOp() {
        return op;
    }

    public void setOp(ByteOp op) {
        this.op = op;
    }
}
