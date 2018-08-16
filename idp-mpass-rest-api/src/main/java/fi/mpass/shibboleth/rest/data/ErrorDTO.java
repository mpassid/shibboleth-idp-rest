/*
 * The MIT License
 * Copyright (c) 2015 CSC - IT Center for Science, http://www.csc.fi
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package fi.mpass.shibboleth.rest.data;

/**
 * Data transfer object for the error API responses.
 */
public class ErrorDTO {
    
    /** The error code. */
    private int code;
    
    /** The error message. */
    private String message;
    
    /** The error fields. */
    private String fields;

    /**
     * Get the error code.
     * @return The error code.
     */
    public int getCode() {
        return code;
    }

    /**
     * Set the error code.
     * @param newCode What to set.
     */
    public void setCode(int newCode) {
        this.code = newCode;
    }

    /**
     * Get the error message.
     * @return The error message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Set the error message.
     * @param newMessage What to set.
     */
    public void setMessage(String newMessage) {
        this.message = newMessage;
    }

    /**
     * Get the error fields.
     * @return The error fields.
     */
    public String getFields() {
        return fields;
    }

    /**
     * Set the error fields.
     * @param newFields What to set.
     */
    public void setFields(String newFields) {
        this.fields = newFields;
    }
}