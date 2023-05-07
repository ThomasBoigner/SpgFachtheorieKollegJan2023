package at.spengergasse.part2.service;

import at.spengergasse.part2.domain.*;
import at.spengergasse.part2.persistence.CustomerRepository;
import at.spengergasse.part2.persistence.PlaylistRepository;
import at.spengergasse.part2.persistence.PodcastRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PodcastServiceTest {

    private PodcastService podcastService;
    private CustomerRepository customerRepository;
    private PlaylistRepository playlistRepository;
    private PodcastRepository podcastRepository;
    private Customer customer;
    private Playlist playlist;
    private Podcast podcast1;
    private Podcast podcast2;

    @BeforeEach
    void setUp(){
        customerRepository = mock(CustomerRepository.class);
        playlistRepository = mock(PlaylistRepository.class);
        podcastRepository = mock(PodcastRepository.class);
        podcastService = new PodcastService(customerRepository, playlistRepository, podcastRepository);

        Advertisement advertisement1 = Advertisement.builder()
                .production(LocalDateTime.of(2022, 6, 1, 1, 1, 1, 1))
                .length(20)
                .productName("product1")
                .miniPlayTime(10)
                .costPerPlay(1)
                .build();

        Advertisement advertisement2 = Advertisement.builder()
                .production(LocalDateTime.of(2022, 6, 2, 1, 1, 1, 1))
                .length(25)
                .productName("product2")
                .miniPlayTime(5)
                .costPerPlay(2)
                .build();

        podcast1= Podcast.builder()
                .production(LocalDateTime.of(2022, 6, 1, 1, 1, 1, 1))
                .length(20)
                .positionForAdd(new ArrayList<>(List.of(1, 2)))
                .maxQuantityAds(3)
                .build();

        podcast2 = Podcast.builder()
                .production(LocalDateTime.of(2022, 6, 2, 1, 1, 1, 1))
                .length(25)
                .positionForAdd(List.of(4, 5))
                .maxQuantityAds(6)
                .build();

        ListenedItem listenedItem1 = ListenedItem.builder()
                .timestamp(LocalDateTime.of(2022, 6, 1, 1, 1, 1, 1))
                .item(advertisement1)
                .build();

        ListenedItem listenedItem2 = ListenedItem.builder()
                .timestamp(LocalDateTime.of(2022, 6, 2, 1, 1, 1, 1))
                .item(advertisement1)
                .build();

        ListenedItem listenedItem3 = ListenedItem.builder()
                .timestamp(LocalDateTime.of(2022, 6, 3, 1, 1, 1, 1))
                .item(advertisement2)
                .build();

        ListenedItem listenedItem4 = ListenedItem.builder()
                .timestamp(LocalDateTime.of(2022, 6, 4, 1, 1, 1, 1))
                .item(advertisement2)
                .build();

        ListenedItem listenedItem5 = ListenedItem.builder()
                .timestamp(LocalDateTime.of(2022, 6, 5, 1, 1, 1, 1))
                .item(podcast2)
                .build();

        ListenedItem listenedItem6 = ListenedItem.builder()
                .timestamp(LocalDateTime.of(2022, 6, 6, 1, 1, 1, 1))
                .item(podcast2)
                .build();

        ListenedItem listenedItem7 = ListenedItem.builder()
                .timestamp(LocalDateTime.of(2022, 6, 7, 1, 1, 1, 1))
                .item(podcast1)
                .build();

        ListenedItem listenedItem8 = ListenedItem.builder()
                .timestamp(LocalDateTime.of(2022, 7, 8, 1, 1, 1, 1))
                .item(podcast1)
                .build();

        advertisement1.setListenedItems(List.of(listenedItem1, listenedItem2));
        advertisement2.setListenedItems(List.of(listenedItem3, listenedItem4));

        customer = Customer.builder()
                .firstname("Thomas")
                .lastname("Boigner")
                .companyName("Spengergasse")
                .registrationDate(LocalDateTime.of(2020, 1, 1, 1, 1, 1, 1))
                .advertisements(List.of(advertisement1, advertisement2))
                .build();

        playlist = Playlist.builder()
                .listenedItems(List.of(listenedItem1, listenedItem2, listenedItem3, listenedItem4, listenedItem5, listenedItem6, listenedItem7, listenedItem8))
                .name("playlist")
                .name("ThomasBoigner")
                .build();

        when(customerRepository.findById(0L)).thenReturn(Optional.of(customer));
    }


    @Test
    void calcTotalCostInvalidCustomerId(){
        // When
        when(customerRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThat(podcastService.calcTotalCosts(0L, LocalDateTime.of(2022, 1, 1, 1, 1, 1), LocalDateTime.of(2023, 1, 1, 1, 1, 1))).isFalse();
    }

    @Test
    void calcTotalCostsAlreadyCalculated(){
        // Given
        customer.setTotalCost(1L);

        // When
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // Then
        assertThat(podcastService.calcTotalCosts(0L, LocalDateTime.of(2022, 1, 1, 1, 1, 1), LocalDateTime.of(2023, 1, 1, 1, 1, 1))).isFalse();
    }

    @Test
    void calcTotalCostInvalidTimePeriod(){
        // When
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // Then
        assertThat(podcastService.calcTotalCosts(0L, LocalDateTime.of(2023, 1, 1, 1, 1, 1), LocalDateTime.of(2022, 1, 1, 1, 1, 1))).isFalse();
    }

    @Test
    void calcTotalCostNoAdvertisements(){
        // When
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // Then
        assertThat(podcastService.calcTotalCosts(0L, LocalDateTime.of(2000, 1, 1, 1, 1, 1), LocalDateTime.of(2001, 1, 1, 1, 1, 1))).isFalse();
    }

    @Test
    void calcTotalCostSuccess(){
        // When
        when(customerRepository.findById(anyLong())).thenReturn(Optional.of(customer));

        // Then
        assertThat(podcastService.calcTotalCosts(0L, LocalDateTime.of(2022, 1, 1, 1, 1, 1), LocalDateTime.of(2023, 1, 1, 1, 1, 1))).isTrue();
        assertThat(customer.getTotalCost()).isNotEqualTo(0);
        assertThat(customer.getTotalCost()).isEqualTo(6);
    }

    @Test
    void calcQuantityInvalidPlaylistId(){
        // When
        when(playlistRepository.findById(0L)).thenReturn(Optional.empty());

        // Then
        assertThat(podcastService.calcQuantityAdditionalAds(0L)).isEqualTo(-3);
    }

    @Test
    void calcQuantityNoPodcasts(){
        // Given
        playlist.setListenedItems(List.of());

        // When
        when(playlistRepository.findById(0L)).thenReturn(Optional.of(playlist));

        // then
        assertThat(podcastService.calcQuantityAdditionalAds(0L)).isEqualTo(-2);
    }

    @Test
    void calcQuantityTooManyAds(){
        // Given
        podcast1.setMaxQuantityAds(0);
        podcast2.setMaxQuantityAds(0);

        // When
        when(playlistRepository.findById(0L)).thenReturn(Optional.of(playlist));

        // Then
        assertThat(podcastService.calcQuantityAdditionalAds(0L)).isEqualTo(-1);
    }

    @Test
    void calcQuantitySuccess(){
        // When
        when(playlistRepository.findById(0L)).thenReturn(Optional.of(playlist));

        // Then
        assertThat(podcastService.calcQuantityAdditionalAds(0L)).isEqualTo(14);
    }

    @Test
    void addPositionForAdInvalidItemId(){
        // When
        when(podcastRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Then
        assertThat(podcastService.addPositionForAd(0L, 1)).isFalse();
    }

    @Test
    void addPositionForAdInvalidPosition(){
        // When
        when(podcastRepository.findById(anyLong())).thenReturn(Optional.of(podcast1));

        // Then
        assertThat(podcastService.addPositionForAd(0L, 21)).isFalse();
    }

    @Test
    void addPositionForAdTooManyAdds(){
        // Given
        podcast1.setMaxQuantityAds(0);
        podcast1.setPositionForAdd(List.of());

        // When
        when(podcastRepository.findById(anyLong())).thenReturn(Optional.of(podcast1));

        // Then
        assertThat(podcastService.addPositionForAd(0, 1)).isFalse();
    }

    @Test
    void addPositionForAdSuccess(){
        // When
        when(podcastRepository.findById(anyLong())).thenReturn(Optional.of(podcast1));

        // Then
        assertThat(podcastService.addPositionForAd(0, 1)).isTrue();
        assertThat(podcast1.getPositionForAdd().size()).isEqualTo(3);
    }
}
