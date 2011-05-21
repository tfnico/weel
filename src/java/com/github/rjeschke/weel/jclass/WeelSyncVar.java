/*
 * Copyright (C) 2011 René Jeschke <rene_jeschke@yahoo.de>
 * See LICENSE.txt for licensing information.
 */
package com.github.rjeschke.weel.jclass;

import com.github.rjeschke.weel.Value;
import com.github.rjeschke.weel.ValueMap;
import com.github.rjeschke.weel.WeelOop;
import com.github.rjeschke.weel.annotations.WeelClass;
import com.github.rjeschke.weel.annotations.WeelMethod;

/**
 * Weel synchronized variable implementation.
 * 
 * @author René Jeschke <rene_jeschke@yahoo.de>
 */
@WeelClass(name = "weel.SyncVar", usesOop = true)
public final class WeelSyncVar
{
    private WeelSyncVar()
    {
        //
    }
    
    @WeelMethod
    public final static void ctor(final ValueMap thiz)
    {
        WeelOop.setInstance(thiz, new SyncVar());
    }

    /**
     * Get the value, blocks until a value was written.
     * 
     * @param thiz
     *            This.
     * @return The value.
     */
    @WeelMethod
    public final static Value get(final ValueMap thiz)
    {
        final SyncVar sv = WeelOop.getInstance(thiz, SyncVar.class);
        return sv.get();
    }

    /**
     * Sets the value.
     * 
     * @param thiz
     *            This.
     * @param value
     *            The value.
     */
    @WeelMethod
    public final static void set(final ValueMap thiz, final Value value)
    {
        final SyncVar sv = WeelOop.getInstance(thiz, SyncVar.class);
        sv.set(value);
    }

    /**
     * Synchronized variable.
     * 
     * @author René Jeschke <rene_jeschke@yahoo.de>
     */
    private final static class SyncVar
    {
        /** Locking object. */
        private Object lock = null;
        /** The value. */
        private volatile Value value;

        /**
         * Constructor.
         */
        public SyncVar()
        {
            //
        }

        /**
         * Gets the value.
         * 
         * @return The value.
         */
        public Value get()
        {
            if (this.lock == null)
            {
                this.lock = new Object();
            }
            synchronized (this.lock)
            {
                try
                {
                    this.lock.wait();
                }
                catch (InterruptedException e)
                {
                    return new Value();
                }
                return this.value;
            }
        }

        /**
         * Sets the value.
         * 
         * @param value
         *            The value.
         */
        public void set(Value value)
        {
            if (this.lock == null)
            {
                this.lock = new Object();
            }
            synchronized (this.lock)
            {
                this.value = value;
                this.lock.notifyAll();
            }
        }
    }
}
