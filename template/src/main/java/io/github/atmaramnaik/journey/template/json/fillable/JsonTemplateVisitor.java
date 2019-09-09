package io.github.atmaramnaik.journey.template.json.fillable;

import io.github.atmaramnaik.journey.core.data.value.Json;
import io.github.atmaramnaik.journey.core.data.value.ValueHolder;
import io.github.atmaramnaik.journey.template.*;
import io.github.atmaramnaik.journey.template.expression.VariableExpression;
import io.github.atmaramnaik.journey.template.expression.operations.RandomInt;

public class JsonTemplateVisitor extends TBaseVisitor<Template<Json>> {
    @Override
    public Template<Json> visitJson(TParser.JsonContext ctx) {
        return visitValue(ctx.value());
    }

    @Override
    public Template<Json> visitObj(TParser.ObjContext ctx) {
        JsonObjectTemplate obj= Template.object();
        for (TParser.PairContext keyValue:
                ctx.pair()) {
            if(keyValue.STRING()!= null){
                obj.with(keyValue.STRING().getText().replaceAll("^\"|\"$", ""),visitValue(keyValue.value()));
            }
            if(keyValue.expression() !=null){
                obj.with(visitExpression(keyValue.expression()),visitValue(keyValue.value()));
            }

        }
        return obj;
    }


    @Override
    public Template<Json> visitArray(TParser.ArrayContext ctx) {
        JsonArrayTemplate array= Template.array();
        for (TParser.ValueContext value:
                ctx.value()) {
            array.with(visitValue(value));
        }
        return array;
    }

    @Override
    public Template<Json> visitDynamicArray(TParser.DynamicArrayContext ctx) {
        DynamicJsonArrayTemplate dArray=Template.array(ctx.name(0).IDENTIFIER().getText(),visitValue(ctx.value()));
        return dArray;
    }

    @Override
    public Template<Json> visitValue(TParser.ValueContext ctx) {
        if( ctx.primitive() != null){
            return visitPrimitive(ctx.primitive());
        }
        if(ctx.obj() != null)
        {
            return visitObj(ctx.obj());
        }
        if(ctx.array() != null){
            return visitArray(ctx.array());
        }
        if(ctx.dynamicArray() != null){
            return visitDynamicArray(ctx.dynamicArray());
        }
        if (ctx.expression()!=null){
            return visitExpression(ctx.expression());
        }
        return null;
    }

    @Override
    public Template<Json> visitPrimitive(TParser.PrimitiveContext ctx) {
        if (ctx.STRING() !=null){
            return Template.value(ctx.STRING().getText().replaceAll("^\"|\"$", ""));
        }
        if(ctx.bool() != null){
            return Template.value(Boolean.parseBoolean(ctx.bool().getText()));
        }
        if(ctx.NUMBER() != null){
            String text = ctx.NUMBER().getText();
            if(text.contains(".")){
                return Template.value(Double.parseDouble(text));
            } else {
                return Template.value(Integer.parseInt(text));
            }
        }
        if(ctx.nullValue() != null){
            return Template.value(null);
        }
        return null;
    }

    @Override
    public Template<Json> visitExpression(TParser.ExpressionContext ctx) {
        return visitExpressionValue(ctx.expressionValue());
    }

    @Override
    public Template<Json> visitExpressionValue(TParser.ExpressionValueContext ctx) {
        if(ctx.expressionFunc()!=null){
            return Template.ex(Template.fun(new RandomInt()));
        }
        if(ctx.primitive()!=null){
            return visitPrimitive(ctx.primitive());
        }
        if(ctx.expressionVariable()!=null){
            VariableExpression variableExpression = Template.var(ctx.expressionVariable().name().IDENTIFIER().getText());
            if(ctx.expressionVariable().type() !=null){
                String type = ctx.expressionVariable().type().name().IDENTIFIER().getText();
                for (Class key:
                        ValueHolder.valueHolderMap.keySet()) {
                    if(key.getSimpleName().equals(type)){
                        variableExpression.ofType(key);
                        return Template.ex(variableExpression);
                    }
                }
                for (Class key:
                        ValueHolder.fallbackValueHolderMap.keySet()) {
                    if(key.getSimpleName().equals(type)){
                        variableExpression.ofType(key);
                        return Template.ex(variableExpression);
                    }
                }

            }
            return Template.ex(variableExpression);
        }
        return null;
    }

    @Override
    public Template<Json> visitExpressionFunc(TParser.ExpressionFuncContext ctx) {
        ctx.name();
        return Template.ex(Template.fun(new RandomInt()));
    }
}
