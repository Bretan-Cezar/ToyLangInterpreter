package model.value;

import model.type.IType;
import model.type.StringType;

import java.util.Objects;

public class StringValue implements IValue {

    private final String string;

    public StringValue(String str) {

        string = str;
    }

    public String getValue() {
        return string;
    }

    @Override
    public String toString() {
        return "\"" + string + "\"";
    }

    @Override
    public IType getType() {
        return new StringType();
    }

    @Override
    public IValue deepCopy() {
        return new StringValue(String.valueOf(string));
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null || getClass() != o.getClass())
            return false;

        StringValue that = (StringValue) o;
        return Objects.equals(string, that.string);
    }

    @Override
    public int hashCode() {
        return Objects.hash(string);
    }

}
