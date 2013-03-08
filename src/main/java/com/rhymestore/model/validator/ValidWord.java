/**
 * Copyright (c) 2010 Enric Ruiz, Ignasi Barrera
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

package com.rhymestore.model.validator;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;

import org.apache.commons.lang.StringUtils;

import com.rhymestore.config.RhymeStore;
import com.rhymestore.lang.WordParser;
import com.rhymestore.lang.WordUtils;

/**
 * Validates that the given property is a valid word, as defined by the {@link WordParser}.
 * 
 * @author Ignasi Barrera
 */
@Documented
@Constraint(validatedBy = ValidWord.Validator.class)
@Target({METHOD, FIELD})
@Retention(RUNTIME)
public @interface ValidWord
{
    String message() default "The input text contains invalid words";

    Class< ? >[] groups() default {};

    Class< ? extends Payload>[] payload() default {};

    static class Validator implements ConstraintValidator<ValidWord, String>
    {
        /** The {@link WordParser} used to validate the word. */
        private WordParser wordParser;

        /** The validation annotation to use. */
        private ValidWord validWord;

        @Override
        public void initialize(final ValidWord constraintAnnotation)
        {
            wordParser = RhymeStore.getWordParser();
            validWord = constraintAnnotation;
        }

        @Override
        public boolean isValid(final String value, final ConstraintValidatorContext context)
        {
            if (StringUtils.isEmpty(value))
            {
                return true;
            }

            String lastWord = WordUtils.getLastWord(value);
            boolean valid = wordParser.isWord(lastWord);

            if (!valid)
            {
                context.disableDefaultConstraintViolation();
                context.buildConstraintViolationWithTemplate(validWord.message() + ": " + value)
                    .addConstraintViolation();
            }

            return valid;
        }
    }
}
