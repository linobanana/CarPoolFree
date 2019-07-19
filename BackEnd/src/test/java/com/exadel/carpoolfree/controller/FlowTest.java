package com.exadel.carpoolfree.controller;

import com.exadel.carpoolfree.model.Car;
import com.exadel.carpoolfree.model.Drive;
import com.exadel.carpoolfree.model.Message;
import com.exadel.carpoolfree.model.PassengerDrive;
import com.exadel.carpoolfree.model.Path;
import com.exadel.carpoolfree.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@Transactional
public class FlowTest {

    private final String DRIVE_API_ROOT = "/api/drive";
    private final String MESSAGE_API_ROOT = "/api/messages";
    private final String PATH_API_ROOT = "/api/path";
    private final String USER_API_ROOT = "/api/users";
    private final String CAR_API_ROOT = "/api/car";
    ObjectMapper objectMapper = new ObjectMapper();
    private Car car = getNextCar();
    private Path path = getNextPath();
    private User user = getNextUser();
    private User user2;
    private Message message = getNextMessage();
    private Drive drive = getNextDrive();
    @Autowired
    private MockMvc mockMvc;
    private JacksonTester<Message> jsonMessage;
    private JacksonTester<Drive> jsonDriveConverter;
    private JacksonTester<Path> jsonPath;
    private JacksonTester<User> jsonUser;
    private JacksonTester<Car> jsonCar;

    @Before
    public void setup() {
        ObjectMapper objectMapper = new ObjectMapper();
        JacksonTester.initFields(this, objectMapper);

        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    }


    @Test
    public void getFlow() throws Exception {

        //read user1
        MvcResult mvcUserResultRead = doRead(USER_API_ROOT + "/" + 1);
        User userRead = objectMapper.readValue(mvcUserResultRead.getResponse().getContentAsString(), User.class);
        this.user = userRead;

        //read user2
        MvcResult mvcUser2ResultRead = doRead(USER_API_ROOT + "/" + 2);
        User user2Read = objectMapper.readValue(mvcUser2ResultRead.getResponse().getContentAsString(), User.class);
        user2 = user2Read;

        //save car
        MvcResult mvcCarResultSaved = doPost(CAR_API_ROOT, car);
        String responseCar = mvcCarResultSaved.getResponse().getContentAsString();
        Car savedCar = objectMapper.readValue(responseCar, Car.class);
        this.car = savedCar;
        Assert.assertNotNull(savedCar.getId());

        //save path
        MvcResult mvcPathResultSaved = doPost(PATH_API_ROOT, path);
        String responsePath = mvcPathResultSaved.getResponse().getContentAsString();
        Path savedPath = objectMapper.readValue(responsePath, Path.class);
        Assert.assertNotNull(savedPath.getId());

        //save message
        MvcResult mvcMessageResultSaved = doPost(MESSAGE_API_ROOT, message);
        String responseMessage = mvcMessageResultSaved.getResponse().getContentAsString();
        Message savedMessage = objectMapper.readValue(responseMessage, Message.class);
        Assert.assertNotNull(savedMessage.getId());

        //save drive
        MvcResult mvcDriveResultSaved = doPost(DRIVE_API_ROOT, drive);
        String responseDrive = mvcDriveResultSaved.getResponse().getContentAsString();
        Drive savedDrive = objectMapper.readValue(responseDrive, Drive.class);
        this.drive = savedDrive;
        Assert.assertNotNull(savedDrive.getId());

        //read car
        MvcResult mvcCarResultRead = doRead(CAR_API_ROOT + "/userId/" + savedCar.getId());
        //Car carRead = jsonCar.parseObject(mvcCarResultRead.getResponse().getContentAsString());
        //Assert.assertEquals(savedCar.getId(), carRead.getId());

        //read path
        MvcResult mvcPathResultRead = doRead(PATH_API_ROOT + "/" + savedPath.getId());

        //read message
        MvcResult mvcMessageResultRead = doRead(MESSAGE_API_ROOT + "/" + savedMessage.getId());

        //read drive
        MvcResult mvcDriveResultRead = doRead(DRIVE_API_ROOT + "/" + savedDrive.getId());

        //delete drive
        doDelete(DRIVE_API_ROOT + "/" + savedDrive.getId());

        //update user
        doUpdate(USER_API_ROOT + "/" + 1, getNextUser());
    }

    private Car getNextCar() {
        Long timestamp = System.currentTimeMillis();
        Long userId = 1L;
        String text = "Test_text";
        return new Car(timestamp, text, text, text, userId);
    }

    private Path getNextPath() {
        Long num = 1L;
        String text = "Test_text";
        Double testDouble = Math.random() * 10;
        return new Path(num, text, testDouble, testDouble);
    }

    private User getNextUser() {
        Long num = 1L;
        String testTxt = "Test text";
        int role = 1;
        List<Car> cars = new ArrayList<>();
        cars.add(getNextCar());
        cars.add(getNextCar());
        List<PassengerDrive> drives = new ArrayList<>();
        User user = new User(num, testTxt, testTxt, testTxt, testTxt, testTxt,
                testTxt, testTxt, role, num, num, cars, drives);
        return user;
    }

    private Message getNextMessage() {
        Long timestamp = 1L;
        String text = "Test_message";

        return new Message(timestamp, text, timestamp, text, user);
    }

    private Drive getNextDrive() {
        Date date = new Date();
        LocalDateTime localDateTime = LocalDateTime.now();
        int test = (int) (1 + Math.random() * 4);
        Double doubleNum = Math.random() * 10;

        List<Message> messages = new ArrayList<>();
        messages.add(getNextMessage());
        messages.add(getNextMessage());

        return new Drive(localDateTime, localDateTime,
                test, user, path, doubleNum);
    }

    private MvcResult doPost(String url, Object data) throws Exception {
        return mockMvc.perform(
                post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private MvcResult doRead(String url) throws Exception {
        return mockMvc.perform(
                get(url))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }

    private ResultActions doDelete(String url) throws Exception {
        return mockMvc.perform(delete(url))
                .andDo(print())
                .andExpect(status().isOk());
    }

    private MvcResult doUpdate(String url, Object data) throws Exception {
        return mockMvc.perform(
                put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(data)))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();
    }
}