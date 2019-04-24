package io.github.atmaramnaik.journey.core.data.value.primitive;


import io.github.atmaramnaik.journey.core.data.value.DeSerializationException;
import io.github.atmaramnaik.journey.core.data.value.primitive.IntegerHolder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class IntegerHolderTest  {
    @Rule
    public ExpectedException expectedException=ExpectedException.none();
    @Test
    public void shouldDeSerializeValidInteger() throws DeSerializationException {
        IntegerHolder integerHolder=new IntegerHolder();
        integerHolder.deSerialize("11");
        assertThat(integerHolder.getValue()).isEqualTo(11);
    }


    @Test
    public void shouldDeSerializeInValidInteger(){
        IntegerHolder integerHolder=new IntegerHolder();

        assertThatThrownBy(()->{
            integerHolder.deSerialize("ab");
        })
                .isInstanceOf(DeSerializationException.class)
                .hasMessage("Value given is not valid Integer");
    }
    @Test
    public void shouldDeSerializeInValidInteger_FolatGiven(){
        IntegerHolder integerHolder=new IntegerHolder();

        assertThatThrownBy(()->{
            integerHolder.deSerialize("1.1");
        })
                .isInstanceOf(DeSerializationException.class)
                .hasMessage("Value given is not valid Integer");
    }
}
