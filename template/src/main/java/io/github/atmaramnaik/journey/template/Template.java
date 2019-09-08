package io.github.atmaramnaik.journey.template;

import io.github.atmaramnaik.journey.core.data.runtime.Context;
import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.core.data.variable.HashMapVariable;
import io.github.atmaramnaik.journey.core.io.IO;
import io.github.atmaramnaik.journey.template.expression.Expression;
import io.github.atmaramnaik.journey.template.expression.Function;
import io.github.atmaramnaik.journey.template.expression.Operation;
import io.github.atmaramnaik.journey.template.expression.VariableExpression;
import io.github.atmaramnaik.journey.template.json.fillable.*;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.*;
import io.github.atmaramnaik.journey.template.json.fillable.*;
import io.github.atmaramnaik.journey.template.json.fillable.extractable.*;
import io.github.atmaramnaik.journey.template.text.SingleVariableTextTemplate;
import io.github.atmaramnaik.journey.template.text.Text;
import io.github.atmaramnaik.journey.template.text.TextTemplate;
import io.github.atmaramnaik.journey.template.text.internals.*;
import io.github.atmaramnaik.journey.template.text.internals.*;

import java.util.ArrayList;
import java.util.HashMap;

public interface Template<T> {
    default ValueHolder<T> process(Context context, IO io){
        HashMapVariable requiredData=getRequiredDataVariables(context);
        context.pour(requiredData.read(io));
        return fill(context);
    }
    T fillReturnValue(Context context);
    default ValueHolder<T> fill(Context context){
        return ValueHolder.getNewValueHolderFor(fillReturnValue(context));
    }
    HashMapVariable getRequiredDataVariables(Context context);
    default HashMapVariable getRequiredDataVariables(Context context,IO io){
        return getRequiredDataVariables(context);
    }


    static JsonObjectTemplate object(){
        return new JsonObjectTemplate(new HashMap<>());
    }
    static <T> StaticObjectTemplate<T> value(T value){
        return new StaticObjectTemplate<>(value);
    }
    static ChooseTemplate choose(String listName, String show, Template<Json> innerTemplate){
        return new ChooseTemplate(listName,show,innerTemplate);
    }
    static ExtractableJsonObjectTemplate object(Text key, ExtractableJsonTemplate<Json> value){
        return new ExtractableJsonObjectTemplate(key,value);
    }
    static ExtractableJsonObjectTemplate object(String key, ExtractableJsonTemplate<Json> value){
        return new ExtractableJsonObjectTemplate(key,value);
    }
    static JsonArrayTemplate array(){
        return new JsonArrayTemplate(new ArrayList<>());
    }
    static ExpressionJsonTemplate ex(Expression expression){
        return new ExpressionJsonTemplate(expression);
    }
    static DynamicJsonArrayTemplate array(String variable, Template<Json> innerElementTemplate){
        return new DynamicJsonArrayTemplate(variable,innerElementTemplate);
    }
    static ExtractableJsonArrayTemplate array(ExtractableJsonTemplate<Json> extractableJsonTemplate){
        return new ExtractableJsonArrayTemplate(extractableJsonTemplate);
    }
    static ExtractableDynamicJsonArrayTemplate array(String variable, ExtractableJsonTemplate<Json> extractableJsonTemplate){
        return new ExtractableDynamicJsonArrayTemplate(variable,extractableJsonTemplate);
    }
    static ExtractableVariableExpressionTemplate xVar(String variable){
        return new ExtractableVariableExpressionTemplate(new VariableExpression(variable));
    }
    static TextTemplate text(Block... blocks){
        TextTemplate textTemplate=new TextTemplate();
        for (Block block:
                blocks) {
            textTemplate.getBlocks().add(block);
        }
        return textTemplate;
    }
     static SingleVariableTextTemplate text(VariableExpression variableExpression){
        SingleVariableTextTemplate singleVariableTextTemplate=new SingleVariableTextTemplate(variableExpression);
        return singleVariableTextTemplate;
    }
     static StaticStringBlock string(String value){
        return new StaticStringBlock(value);
    }
     static ListBlock textLoop(String variable){
        return new ListBlock(variable);
    }
     static ExpressionBlock textEx(Expression expression){
        return new ExpressionBlock(expression);
    }
    static Function function(Operation operation){
        return new Function(operation);
    }
    static VariableExpression var(String variableName){
        return new VariableExpression(variableName);
    }
    static Function fun(Operation operation){
        return new Function(operation);
    }
    static ChoiceBlock choose(String listName, String show, Block innerBlock){
        return new ChoiceBlock(listName,show,innerBlock);
    }
}
