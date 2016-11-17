import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by cthill on 11/16/16.
 */
public class TransferClient {
    protected Socket socket;
    protected BufferedReader socketIn;
    protected DataOutputStream socketOut;

    public TransferClient(String address, int port) throws IOException {
        this.socket = new Socket(address, port);
        this.socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.socketOut = new DataOutputStream(socket.getOutputStream());

        //TODO: send login information
    }

    public void transfer(String sourceFilename, String destFilename) throws FileNotFoundException, IOException {
        // load file
        File file = new File(sourceFilename);
        BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));

        // calculate chunks
        long size = file.length();
        int chunks = (int) size / Constants.CHUNK_SIZE;
        // chunks are calculated using integer division (which floors), so we might need to add another chunk
        if (size % Constants.CHUNK_SIZE != 0) {
            chunks++;
        }

        //TODO: send packet header to indicate initiation of file transfer
        //TODO: send filename, file size, chunk size, etc.

        // read each chunk
        for (int i = 0; i < chunks; i++) {
            // read <CHUNK_SIZE> bytes into the buffer
            // buffer is padded with 0's if the file size is not divisible by <CHUNK_SIZE>

            // create buffer
            byte[] buffer = new byte[Constants.CHUNK_SIZE];

            // calculate bytes to read from file
            int readLength = Constants.CHUNK_SIZE;
            if (i == chunks - 1) {
                readLength = (int) size % Constants.CHUNK_SIZE;
            }

            // read bytes
            bis.read(buffer, 0, readLength);

            //TODO: hash chunk

            //TODO: base64 encode

            //TODO: xor cipher

            //TODO: send packet header to indicate incoming chunk
            //TODO: send chunk number (i)
            //TODO write bytes to socket
        }
    }

    public void disconnect() throws IOException {
        socket.close();
    }
}
