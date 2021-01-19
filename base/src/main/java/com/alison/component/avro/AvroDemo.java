package com.alison.component.avro;

import org.apache.avro.file.DataFileReader;
import org.apache.avro.file.DataFileWriter;
import org.apache.avro.io.DatumReader;
import org.apache.avro.io.DatumWriter;
import org.apache.avro.specific.SpecificDatumReader;
import org.apache.avro.specific.SpecificDatumWriter;

import java.io.File;

public class AvroDemo {

    public static void main(String[] args) throws Exception {
        AvroDemo avroDemo = new AvroDemo();
        avroDemo.testSerializing();
    }

    public void testSerializing() throws Exception {
        Person person = new Person("001", "zhangsan", 23);
        DatumWriter dw = new SpecificDatumWriter<Person>(Person.class);
        DataFileWriter<Person> dfw = new DataFileWriter<>(dw);

        dfw.create(person.getSchema(), new File("D:\\person.avro"));
        dfw.append(person);
        dfw.close();
    }

    public void testDeSerializing() throws Exception {
        DatumReader<Person> dr = new SpecificDatumReader<Person>(Person.class);
        DataFileReader<Person> dfr = new DataFileReader<Person>(new File("D:\\person.avro"), dr);
        Person person = null;
        while (dfr.hasNext()) {
            person = dfr.next();
            System.out.println(person);
        }
    }


}
