package com.noreasonexception.datanuke.app.dataProvider;

import javax.swing.text.html.Option;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ByteChannel;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.NoSuchElementException;
import java.util.Optional;

import static java.util.Optional.of;

public class FileDataProvider extends DataProvider {
    ByteChannel channel = null;
    ByteBuffer  buffer=null;
    public FileDataProvider(Path file){
        try{
            this.buffer=ByteBuffer.allocate((int)Files.readAttributes(file,BasicFileAttributes.class).size());
            this.channel= Files.newByteChannel(file);
        }catch (IOException e){
            this.channel=null;
        }

    }

    @Override
    public Optional<Buffer> provide() {
        try {
            this.channel.read(this.buffer);
            this.buffer.rewind();
            return Optional.of(this.buffer);

        }catch (IOException|NullPointerException e){
            return Optional.empty();
        }

    }
}
