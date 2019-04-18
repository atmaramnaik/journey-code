package io.github.atmaramnaik.journey.template.template.expression;

import io.github.atmaramnaik.journey.template.data.runtime.Context;
import io.github.atmaramnaik.journey.template.data.value.ValueHolder;
import io.github.atmaramnaik.journey.template.data.value.primitive.StringHolder;
import io.github.atmaramnaik.journey.template.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.template.data.variable.Readable;
import io.github.atmaramnaik.journey.template.data.variable.ValueHolderVariable;
import org.junit.Test;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static io.github.atmaramnaik.journey.template.template.Template.*;
public class FunctionTest {
    Operation mockOperation=(List<ValueHolder> valueHolders)->{
        String ret="";
        for (ValueHolder valueHolder:valueHolders) {
            ret+=(String)valueHolder.getValue();
        }
        StringHolder stringHolder=new StringHolder();
        stringHolder.setValue(ret);
        return stringHolder;
    };
    @Test
    public void shouldGetVariables(){
        Function function=function(mockOperation)
                .withArgumet(var("var1").ofType(String.class))
                .withArgumet(var("var2").ofType(String.class));
        HashMapVariable hashMapVariable=function.getRequiredDataVariables(new Context());
        HashMap<String, Readable> dataDescription=hashMapVariable.getDataDescription();
        assertThat(dataDescription).containsKey("var1");
        Readable variableDescription=hashMapVariable.getDataDescription().get("var1");
        assertThat(variableDescription).isInstanceOf(ValueHolderVariable.class);
        ValueHolderVariable<String> valueHolderVariable=(ValueHolderVariable<String>)variableDescription;
        assertThat(valueHolderVariable.getValueHolderClass()).isEqualTo(StringHolder.class);

        assertThat(dataDescription).containsKey("var2");
        Readable variableDescription1=hashMapVariable.getDataDescription().get("var2");
        assertThat(variableDescription1).isInstanceOf(ValueHolderVariable.class);
        ValueHolderVariable<String> valueHolderVariable1=(ValueHolderVariable<String>)variableDescription;
        assertThat(valueHolderVariable1.getValueHolderClass()).isEqualTo(StringHolder.class);
    }
    @Test
    public void shouldFillVariables(){
        Function function=function(mockOperation)
                .withArgumet(var("var1").ofType(String.class))
                .withArgumet(var("var2").ofType(String.class));
        Context context=new Context(new HashMap<>());
        context.getCurrentContext().put("var1","Hello");
        context.getCurrentContext().put("var2","World");
        String value=function.fill(context).serialize();

        assertThat(value).isEqualTo("HelloWorld");
    }
}
