/*
 * Copyright (C) 2011 René Jeschke <rene_jeschke@yahoo.de>
 * See LICENSE.txt for licensing information.
 */
package com.github.rjeschke.weel;

class InstrDoForEach implements Instr
{
    /** @see Instr#getType() */
    @Override
    public Op getType()
    {
        return Op.DOFOREACH;
    }

    /** @see java.lang.Object#toString() */
    @Override
    public String toString()
    {
        return "DOFOREACH";
    }

    /** @see Instr#write(JvmMethodWriter) */
    @Override
    public void write(JvmMethodWriter mw)
    {
        mw.aload(0);
        mw.invokeVirtual("com.github.rjeschke.weel.WeelRuntime", "doForEach", "()Z");
    }
}
