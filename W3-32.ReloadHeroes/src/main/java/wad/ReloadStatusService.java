package wad;

import java.util.List;
import java.util.ArrayList;
import java.util.UUID;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class ReloadStatusService {

    @Autowired
    private ReloadStatusRepository reloadStatusRepository;
    @Autowired
    private HttpSession session;

    /**
     *
     * @return palauttaa viisi eniten uudelleenlatauksia tehnyttä käyttäjää
     * suuruusjärjestyksessä. Listan ensimmäisellä sijalla on eniten
     * uudelleenlatauksia tehnyt henkilö, toisella sijalla toiseksi eniten jne.
     */
    public List<ReloadStatus> getTopList() {
        Pageable pageable = new PageRequest(0, 5, Sort.Direction.DESC, "reloads");
        Page<ReloadStatus> reloadsPage = reloadStatusRepository.findAll(pageable);
        return reloadsPage.getContent();
    }

    /**
     *
     * @return palauttaa pyynnön tehneeseen henkilöön liittyvän
     * ReloadStatus-olion. Jos käyttäjä ei ole tehnyt yhtäkään pyyntöä aiemmin,
     * tulee käyttäjälle luoda uusi tunnus sekä alustaa uudelleenlatausten määrä
     * yhteen.
     */
    public ReloadStatus reload() {
        ReloadStatus status = null;

        if (session.getAttribute("name") != null) {
            String name = (String) session.getAttribute("name");
            status = reloadStatusRepository.findByName(name);
        }

        if (status == null) {
            status = new ReloadStatus();
            status.setName(UUID.randomUUID().toString());
            status.setReloads(0);

            session.setAttribute("name", status.getName());
        }

        status.setReloads(status.getReloads() + 1);

        return reloadStatusRepository.save(status);
    }
}
