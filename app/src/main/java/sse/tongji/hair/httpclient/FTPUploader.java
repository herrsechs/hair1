package sse.tongji.hair.httpclient;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

/**
 * Created by LLLLLyj on 2015/9/6.
 */
public class FTPUploader {
    protected int port;
    protected String url;
    protected String username;
    protected String password;
    protected FTPClient ftp = new FTPClient();

    public FTPUploader(){
        this.port = 21;
        this.url = "123.56.148.119";
        this.username = "gamino";
        this.password = "lyj5654582";
    }

    public FTPUploader(int port, String url, String username, String password){
        this.port = port;
        this.url = url;
        this.username = username;
        this.password = password;
    }

    public boolean uploadFile(String storePath, String filename, InputStream input){
        try{
            ftp.connect(url, port);
            boolean login_result = ftp.login(this.username, this.password);
            if(!login_result){
                ftp.disconnect();
                System.out.println("Fail to log in!");
                return false;
            }

            int reply = ftp.getReplyCode();
            ftp.makeDirectory(storePath);
            if(!FTPReply.isPositiveCompletion(reply)){
                ftp.disconnect();
                return false;
            }

            ftp.enterLocalPassiveMode();
            ftp.setFileType(FTP.BINARY_FILE_TYPE);
            ftp.changeWorkingDirectory(storePath);
            ftp.storeFile(filename, input);

            input.close();
            ftp.logout();
        }catch(SocketException e){
            e.printStackTrace();
            return false;
        }catch(IOException e){
            e.printStackTrace();
            return false;
        }
        finally {
            if(ftp.isConnected()){
                try{
                    ftp.disconnect();
                }catch(IOException e){
                    e.printStackTrace();
                    return false;
                }
            }
        }
        return true;
    }
}
