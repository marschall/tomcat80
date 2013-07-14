/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/* Generated By:JJTree: Do not edit this line. AstLambdaExpression.java Version 4.3 */
package org.apache.el.parser;

import java.util.HashMap;
import java.util.Map;

import javax.el.ELException;

import org.apache.el.lang.EvaluationContext;
import org.apache.el.util.MessageFactory;

public class AstLambdaExpression extends SimpleNode {

    public AstLambdaExpression(int id) {
        super(id);
    }

    @SuppressWarnings("null") // paramValues[i] can't be null due to checks
    @Override
    public Object invoke(EvaluationContext ctx, Class<?>[] paramTypes,
            Object[] paramValues) throws ELException {

        // Two children - the formal parameters and the expression
        AstLambdaParameters formalParameters =
                (AstLambdaParameters) children[0];

        int paramCount = 0;
        if (formalParameters.children != null) {
            paramCount = formalParameters.children.length;
        }
        int argCount = 0;
        if (paramValues != null) {
            argCount = paramValues.length;
        }
        if (paramCount > argCount) {
            throw new ELException(MessageFactory.get("error.args.tooFew",
                    Integer.valueOf(argCount), Integer.valueOf(paramCount)));
        }

        // Build the argument map
        Map<String,Object> lambdaArgumnents = new HashMap<>();
        for (int i = 0; i < paramCount; i++) {
            lambdaArgumnents.put(formalParameters.children[i].getImage(),
                    paramValues[i]);
        }

        ctx.enterLambdaScope(lambdaArgumnents);

        try {
            return children[1].getValue(ctx);
        } finally {
            ctx.exitLambdaScope();
        }
    }
}
/* JavaCC - OriginalChecksum=071159eff10c8e15ec612c765ae4480a (do not edit this line) */
