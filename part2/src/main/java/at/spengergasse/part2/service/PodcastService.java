package at.spengergasse.part2.service;

import at.spengergasse.part2.domain.*;
import at.spengergasse.part2.persistence.CustomerRepository;
import at.spengergasse.part2.persistence.PlaylistRepository;
import at.spengergasse.part2.persistence.PodcastRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor

@Service
public class PodcastService {

    private final CustomerRepository customerRepository;
    private final PlaylistRepository playlistRepository;
    private final PodcastRepository podcastRepository;

    public boolean calcTotalCosts(long customerId, LocalDateTime begin, LocalDateTime end){
        Optional<Customer> entity = customerRepository.findById(customerId);

        if (entity.isEmpty())
            return false;

        Customer customer = entity.get();

        if (customer.getTotalCost() != 0)
            return false;

        if (end.isBefore(begin))
            return false;

        List<Advertisement> advertisements = customer.getAdvertisements().stream().filter(advertisement ->
                advertisement.getListenedItems().stream().filter(listenedItem ->
                        listenedItem.getTimestamp().isAfter(begin) && listenedItem.getTimestamp().isBefore(end)).toList().size() > 0).toList();

        if (advertisements.size() == 0)
            return false;

        customer.setTotalCost(advertisements.stream().mapToDouble(advertisement -> advertisement.getCostPerPlay() * advertisement.getListenedItems().size()).sum());
        return true;
    }

    public int calcQuantityAdditionalAds(long playlistId){
        Optional<Playlist> entity = playlistRepository.findById(playlistId);

        if (entity.isEmpty())
            return -3;

        Playlist playlist = entity.get();
        List<Podcast> podcasts = playlist.getListenedItems().stream().map(ListenedItem::getItem)
                .filter(item -> item instanceof Podcast)
                .map(item -> (Podcast)item).toList();

        if (podcasts.size() == 0){
            return -2;
        }

        List<Advertisement> advertisements = playlist.getListenedItems().stream().map(ListenedItem::getItem)
                .filter(item -> item instanceof Advertisement)
                .map(item -> (Advertisement)item).toList();

        int maxNumberOfAds = podcasts.stream().mapToInt(Podcast::getMaxQuantityAds).sum();

        if (advertisements.size() > maxNumberOfAds)
            return -1;

        return maxNumberOfAds - advertisements.size();
    }

    public boolean addPositionForAd(long itemId, int position){
        Optional<Podcast> entity = podcastRepository.findById(itemId);

        if (entity.isEmpty())
            return false;

        Podcast podcast = entity.get();

        if (podcast.getLength() < position)
            return false;

        if (podcast.getPositionForAdd().size() + 1 > podcast.getMaxQuantityAds())
            return false;

        podcast.getPositionForAdd().add(position);
        return true;
    }
}
