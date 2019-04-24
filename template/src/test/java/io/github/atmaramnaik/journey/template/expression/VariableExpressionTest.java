package io.github.atmaramnaik.journey.template.expression;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.data.value.primitive.IntegerHolder;
import io.github.atmaramnaik.journey.core.data.value.primitive.StringHolder;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.core.data.variable.Readable;
import io.github.atmaramnaik.journey.core.data.variable.ValueHolderVariable;
import io.github.atmaramnaik.journey.template.template.expression.VariableExpression;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static io.github.atmaramnaik.journey.template.template.Template.*;
public class VariableExpressionTest {
    @Test
    public void shouldFillSingleIntegerVariable(){
        VariableExpression variableExpression =var("var");
        Map<String,Object> contextData=new HashMap<>();
        contextData.put("var",123);
        Context context=new Context(contextData);
        ValueHolder valueHolder= variableExpression.fill(context);
        Object value=valueHolder.getValue();
        assertThat(value).isEqualTo(123);
    }
    @Test
    public void shouldGetSingleIntegerVariable(){
        VariableExpression variableExpression =var("var")
        .ofType(Integer.class);
        Map<String,Object> contextData=new HashMap<>();
        Context context=new Context(contextData);
        HashMapVariable hashMapVariable = variableExpression.getRequiredDataVariables(context);
        HashMap<String, Readable> dataDescription=hashMapVariable.getDataDescription();
        assertThat(dataDescription).containsKey("var");
        Readable variableDescription=hashMapVariable.getDataDescription().get("var");
        assertThat(variableDescription).isInstanceOf(ValueHolderVariable.class);
        ValueHolderVariable<Integer> valueHolderVariable=(ValueHolderVariable<Integer>)variableDescription;
        assertThat(valueHolderVariable.getValueHolderClass()).isEqualTo(IntegerHolder.class);
    }
    @Test
    public void shouldGetSingleStringVariable(){
        VariableExpression variableExpression =var("var")
                .ofType(String.class);
        Map<String,Object> contextData=new HashMap<>();
        Context context=new Context(contextData);
        HashMapVariable hashMapVariable = variableExpression.getRequiredDataVariables(context);
        HashMap<String, Readable> dataDescription=hashMapVariable.getDataDescription();
        assertThat(dataDescription).containsKey("var");
        Readable variableDescription=hashMapVariable.getDataDescription().get("var");
        assertThat(variableDescription).isInstanceOf(ValueHolderVariable.class);
        ValueHolderVariable<String> valueHolderVariable=(ValueHolderVariable<String>)variableDescription;
        assertThat(valueHolderVariable.getValueHolderClass()).isEqualTo(StringHolder.class);
    }
}
