package miniblog.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Commons {
    
    //Convert byte data to hex 
    private static String convertToHex(byte[] data)
    {
        //create StringBuffer
        StringBuffer buf = new StringBuffer();
        //Loop to read each data 
        for (int i = 0; i < data.length; i++)
        {
            int halfbyte = (data[i] >>> 4) & 0x0F;
            int two_halfs = 0;
            //Calculator and conver to hex
            do
            {
                if ((0 <= halfbyte) && (halfbyte <= 9))
                    buf.append((char) ('0' + halfbyte));
                else
                    buf.append((char) ('a' + (halfbyte - 10)));
                halfbyte = data[i] & 0x0F;
            } 
            // if < 1 turn back
            while (two_halfs++ < 1);
        }
        //return String after convert to hex
        return buf.toString();
    }

    //Input String and convert password
    public String MD5(String text)
    {
        //Create digest object
        MessageDigest md = null;
        try
        {
            //set type of password
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e)
        {
            //catch error
            e.printStackTrace();
        }
        // create byte data 32bit
        byte[] md5hash = new byte[32];
        try
        {
            //Updates the digest using the specified array of bytes, starting at the specified offset
            md.update(text.getBytes("iso-8859-1"), 0, text.length());
        } catch (UnsupportedEncodingException e)
        {
            //catch error
            e.printStackTrace();
        }
        //Completes the hash computation 
        md5hash = md.digest();
        //return password have been converted type MD5
        return convertToHex(md5hash);
    }
}
