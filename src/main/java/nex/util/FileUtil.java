/*
 * NetherEx
 * Copyright (c) 2016-2017 by LogicTechCorp
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation version 3 of the License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package nex.util;

import nex.NetherEx;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FileUtil
{
    public static void copyToDirectory(File sourcePath, File destinationPath)
    {
        try
        {
            if(!destinationPath.exists())
            {
                destinationPath.mkdir();
            }

            FileUtils.copyDirectory(sourcePath, destinationPath);
        }
        catch(IOException ignored)
        {
        }
    }

    public static void extractToDirectory(File sourcePath, File destinationPath)
    {
        if(!destinationPath.exists())
        {
            destinationPath.mkdir();
        }

        FileUtil.extractFromJar(sourcePath.getPath(), destinationPath.getPath());
    }

    private static void extractFromJar(String sourcePath, String destinationPath)
    {
        URL sourceURL = NetherEx.class.getResource(sourcePath);
        String fromPath = sourcePath.substring(1);

        if(sourceURL != null && sourceURL.getProtocol().equals("jar"))
        {
            try
            {
                JarURLConnection jarURLConnection = (JarURLConnection) sourceURL.openConnection();
                ZipFile zipFile = jarURLConnection.getJarFile();
                Enumeration<? extends ZipEntry> zipEntries = zipFile.entries();

                while(zipEntries.hasMoreElements())
                {
                    ZipEntry zipEntry = zipEntries.nextElement();
                    String zipName = zipEntry.getName();

                    if(!zipName.startsWith(fromPath))
                    {
                        continue;
                    }

                    String pathTail = zipName.substring(fromPath.length());
                    File file = new File(destinationPath + File.separator + pathTail);

                    if(!file.exists())
                    {
                        if(zipEntry.isDirectory())
                        {
                            file.mkdir();
                        }
                        else
                        {
                            InputStream inputStream = zipFile.getInputStream(zipEntry);
                            OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file));
                            byte buffer[] = new byte[4096];
                            int count;

                            while((count = inputStream.read(buffer)) > 0)
                            {
                                outputStream.write(buffer, 0, count);
                            }

                            inputStream.close();
                            outputStream.close();
                        }
                    }
                }
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
