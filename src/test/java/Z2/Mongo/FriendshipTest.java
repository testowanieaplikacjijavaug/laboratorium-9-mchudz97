package Z2.Mongo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import Z2.Mongo.*;

public class FriendshipTest {

    FriendshipsMongo friendships;

    FriendsCollection friends;



    //A nice mock expects recorded calls in any order and returning null for other calls
    @BeforeEach
    public void resetMock(){
        friendships  = new FriendshipsMongo();
        friends = mock(FriendsCollection.class);
        friendships.friends = friends;


    }

    @Test
    public void mockingWorksAsExpected(){
        Person joe = new Person("Joe");

        when(friends.findByName("Joe")).thenReturn(joe);
        assertThat(friends.findByName("Joe")).isEqualTo(joe);
        verify(friends).findByName("Joe");
    }

    @Test
    public void alexDoesNotHaveFriends(){

        assertThat(friendships.getFriendsList("Alex")).isEmpty();

    }

    @Test
    public void joeHas5Friends(){

        List<String> expected = Arrays.asList(new String[]{"Karol","Dawid","Maciej","Tomek","Adam"});
        Person joe = mock(Person.class);

        //doReturn(joe).when(friends.findByName("Joe"));
        //doReturn(expected).when(joe.getFriends());
        when(friends.findByName("Joe")).thenReturn(joe);
        when(joe.getFriends()).thenReturn(expected);

        assertThat(friendships.getFriendsList("Joe")).hasSize(5).containsOnly("Karol","Dawid","Maciej","Tomek","Adam");

        verify(friends).findByName("Joe");
        verify(joe).getFriends();

    }

    @Test
    public void areFriendsTest(){


        List<String> listMock = mock(List.class);
        Person kordjasz = mock(Person.class);

        when(friends.findByName("kordjasz")).thenReturn(kordjasz);
        when(kordjasz.getFriends()).thenReturn(listMock);
        when(listMock.contains("marjusz")).thenReturn(false);


        assertThat(friendships.areFriends("kordjasz", "marjusz")).isFalse();

        verify(friends).findByName("kordjasz");
        verify(kordjasz).getFriends();
        verify(listMock).contains("marjusz");

    }

    @Test
    public void areFriendsTest2(){


        List<String> listMock = mock(List.class);
        Person kordjasz = mock(Person.class);
        when(friends.findByName("kordjasz")).thenReturn(kordjasz);
        when(kordjasz.getFriends()).thenReturn(listMock);
        when(listMock.contains("marjusz")).thenReturn(true);


        assertThat(friendships.areFriends("kordjasz", "marjusz")).isTrue();

        verify(friends).findByName("kordjasz");
        verify(kordjasz).getFriends();
        verify(listMock).contains("marjusz");

    }
    @Test
    public void areFriendsTest_exception(){


        List<String> listMock = mock(List.class);
        Person kordjasz = mock(Person.class);

        when(friends.findByName("kordjasz")).thenReturn(kordjasz);
        when(kordjasz.getFriends()).thenReturn(listMock);
        when(listMock.contains("marjusz")).thenThrow(new IllegalArgumentException("co"));

        assertThatThrownBy(()->friendships.areFriends("kordjasz", "marjusz")).isInstanceOf(IllegalArgumentException.class);

        verify(friends).findByName("kordjasz");
        verify(kordjasz).getFriends();
        verify(listMock).contains("marjusz");

    }

    @Test
    public void addFrTestEx(){

        when(friends.findByName("kordjasz")).thenThrow(new IllegalArgumentException("spoko"));

        assertThatThrownBy(
                ()->friendships.addFriend("kordjasz","aha"))
                .isInstanceOf(IllegalArgumentException.class);

        verify(friends).findByName("kordjasz");

    }
    @Test
    public void makeFriendsTestEx(){


        when(friends.findByName("kordjasz")).thenThrow(new IllegalArgumentException("ta"));

        assertThatThrownBy(()-> friendships.makeFriends("kordjasz", "marjusz")).isInstanceOf(IllegalArgumentException.class);

        verify(friends).findByName("kordjasz");

    }


}
