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

package com.rhymestore.lang.es;

import java.math.BigDecimal;

/**
 * This enum provides a base sound.
 * 
 * @todo rest of multiples. Comment!
 * @author Serafin Sedano
 * @version 0.1
 */
public enum SpanishNumber
{
    UNO("1", "uno"), DOS("2", "dos"), TRES("3", "tres"), CUATRO("4", "cuatro"), CINCO("5", "cinco"), SEIS(
        "6", "seis"), SIETE("7", "siete"), OCHO("8", "ocho"), NUEVE("9", "nueve"), DIEZ("10",
        "diez"), ONCE("11", "once"), DOCE("12", "doce"), TRECE("13", "trece"), CATORCE("14",
        "catorce"), QUINCE("15", "quince"), CERO("0", "cero"), VEINTE("20", "veinte"), DECENAS(
        "70", "enta"), CIEN("100", "cien"), CIENTOS("700", "cientos"), MIL("7000", "mil"), MILLON(
        "1000000", "millón"), MILLONES("700000000", "millones");

    public static String getBaseSound(long number)
    {
        return getBaseSound(String.valueOf(number));
    }

    public static String getBaseSound(BigDecimal number)
    {
        return getBaseSound(number.toPlainString());
    }

    public static String getBaseSound(String number)
    {
        if ((number == null) || "".equals(number))
        {
            return null;
        }

        number = trim(number);
        if (new BigDecimal(number).compareTo(BigDecimal.valueOf(Long.MAX_VALUE)) > 0)
        {
            number = handlingValue(number);
        }
        Long n = Long.valueOf(number);
        // no leading zeros
        number = n.toString();
        char[] digits = number.toCharArray();
        switch (number.length())
        {
            case 1:
            {
                return getWordByNumber(number);
            }
            case 2:
            {
                return tenners(n);
            }
            case 3:
            {
                // es un ciento *
                if (n % 100 == 0)
                {
                    // 100
                    if (digits[0] == '1')
                    {
                        return getWordByNumber(n);
                    }
                    return getWordByNumber("700");
                }
                // 10 - 15
                return tenners(Integer.parseInt(digits[1] + "" + digits[2]));
            }
            case 4:
            case 5:
            {
                // mil
                if (n % 1000 == 0)
                {
                    return getWordByNumber("7000");
                }
                if (n % 100 == 0)
                {
                    // 100
                    if (digits[0] == 1)
                    {
                        return getWordByNumber(n);
                    }
                    return getWordByNumber(700);
                }
                // 10 - 15
                return tenners(Integer.parseInt(digits[digits.length - 2] + ""
                    + digits[digits.length - 1]));
            }
            default:
            {
                // recursividad!

                if (n % 1000000 == 0)
                {
                    if (n == 1000000)
                    {
                        return MILLON.getWord();
                    }
                    return MILLONES.getWord();
                }
                else if (n % 100000 == 0)
                {
                    return MIL.getWord();
                }
                if (number.length() == 1)
                {
                    return getWordByNumber(number);
                }
                return getBaseSound(number.substring(1));
            }
        }

    }

    private static String trim(String number2)
    {
        int comma = number2.lastIndexOf(",");
        if (comma > -1 && comma < number2.length())
        {
            number2 = number2.substring(comma + 1, number2.length());
        }
        int dot = number2.lastIndexOf(".");
        if (dot > -1 && dot < number2.length())
        {
            number2 = number2.substring(dot + 1, number2.length());
        }
        int period = number2.lastIndexOf(":");
        if (period > -1 && period < number2.length())
        {
            number2 = number2.substring(period + 1, number2.length());
        }
        int eq = number2.lastIndexOf("=");
        if (eq > -1 && eq < number2.length())
        {
            number2 = number2.substring(eq + 1, number2.length());
        }
        if (number2.contains("+"))
        {
            number2 = number2.replaceAll("+", "");
        }
        if (number2.contains("-"))
        {
            number2 = number2.replaceAll("-", "");
        }
        if (number2.contains("E"))
        {
            number2 = number2.replaceAll("E", "");
        }
        return number2;
    }

    public static String getWordByNumber(long number)
    {
        for (SpanishNumber n : values())
        {
            if (n.getIntValue() == (number))
            {
                return n.getWord();
            }
        }
        return null;
    }

    public static String getWordByNumber(String number)
    {
        for (SpanishNumber n : values())
        {
            if (n.getNumber().equalsIgnoreCase(number))
            {
                return n.getWord();
            }
        }
        return null;
    }

    private static String handlingValue(String number)
    {
        String n = number.substring(number.length() - 7, number.length());
        if ("0000000".equals(n))
        {
            return MILLONES.getNumber();
        }
        return n;
    }

    private static String tenners(long n)
    {
        long rest = n % 10;
        if ((n == 10) || (n == 20))
        {
            return getWordByNumber(n);
        }
        if (n >= 20)
        {
            if (rest == 0)
            {
                return DECENAS.getWord();
            }
            return getWordByNumber(rest);
        }
        if (rest > 5)
        {
            return getWordByNumber(rest);
        }
        return getWordByNumber(n);
    }

    private final String number;

    private final String word;

    private final int n;

    private SpanishNumber(String number, String word)
    {
        this.number = number;
        this.word = word;
        this.n = Integer.valueOf(number);
    }

    /* package */int getIntValue()
    {
        return this.n;
    }

    /* package */String getNumber()
    {
        return this.number;
    }

    /* package */String getWord()
    {
        return this.word;
    }
}
