package Z2.Mongo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

public class FriendshipTest {




    FriendshipsMongo friendships = new FriendshipsMongo();

    //A nice mock expects recorded calls in any order and returning null for other calls
    @Mock
    FriendsCollection friends;

    @Test
    public void mockingWorksAsExpected(){
        Person joe = new Person("Joe");

        when(friends.findByName("Joe")).thenReturn(joe);
        assertThat(friends.findByName("Joe")).isEqualTo(joe);
        verify(friends.findByName("Joe"));
    }

    @Test
    public void alexDoesNotHaveFriends(){

        assertThat(friendships.getFriendsList("Alex")).isEmpty();
    }

    @Test
    public void joeHas5Friends(){

        List<String> expected = Arrays.asList(new String[]{"Karol","Dawid","Maciej","Tomek","Adam"});
        Person joe = mock(Person.class);

        doReturn(joe).when(friends.findByName("Joe"));
        doReturn(expected).when(joe.getFriends());


        assertThat(friendships.getFriendsList("Joe")).hasSize(5).containsOnly("Karol","Dawid","Maciej","Tomek","Adam");

        verify(friends).findByName("Joe");
        verify(joe).getFriends();

    }

    @Test
    public void areFriendsTest(){


        List<String> listMock = mock(List.class);
        Person kordjasz = mock(Person.class);

        doReturn(kordjasz).when(friends.findByName("kordjasz"));
        doReturn(listMock).when(kordjasz.getFriends());
        doReturn(false).when(listMock.contains("marjusz"));


        assertThat(friendships.areFriends("kordjasz", "marjusz")).isFalse();

        verify(friends).findByName("kordjasz");
        verify(kordjasz).getFriends();
        verify(listMock).contains("marjusz");

    }

    @Test
    public void areFriendsTest2(){


        List<String> listMock = mock(List.class);
        Person kordjasz = mock(Person.class);
        doReturn(kordjasz).when(friends.findByName("kordjasz"));
        doReturn(listMock).when(kordjasz.getFriends());
        doReturn(true).when(listMock.contains("marjusz"));


        assertThat(friendships.areFriends("kordjasz", "marjusz")).isTrue();

        verify(friends).findByName("kordjasz");
        verify(kordjasz).getFriends();
        verify(listMock).contains("marjusz");

    }
    @Test
    public void areFriendsTest_exception(){


        List<String> listMock = createMock(List.class);
        Person kordjasz = createMock(Person.class);
        expect(friends.findByName("kordjasz")).andReturn(kordjasz);
        expect(kordjasz.getFriends()).andReturn(listMock);
        expect(listMock.contains("marjusz")).andThrow(new IllegalArgumentException("co"));
        EasyMock.replay(friends);
        EasyMock.replay(listMock);
        EasyMock.replay(kordjasz);

        assertThatThrownBy(()->friendships.areFriends("kordjasz", "marjusz")).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void addFrTestEx(){



        expect(friends.findByName("kordjasz")).andThrow(new IllegalArgumentException("spoko"));
        EasyMock.replay(friends);

        assertThatThrownBy(()->friendships.addFriend("kordjasz","aha")).isInstanceOf(IllegalArgumentException.class);


    }
    @Test
    public void makeFriendsTestEx(){


        expect(friends.findByName("kordjasz")).andThrow(new IllegalArgumentException("ta"));
        EasyMock.replay(friends);

        assertThatThrownBy(()-> friendships.makeFriends("kordjasz", "marjusz")).isInstanceOf(IllegalArgumentException.class);



    }


}
