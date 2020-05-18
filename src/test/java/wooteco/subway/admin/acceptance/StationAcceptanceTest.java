package wooteco.subway.admin.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import wooteco.subway.admin.dto.StationResponse;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class StationAcceptanceTest extends AcceptanceTest {
    @DisplayName("지하철역을 관리한다")
    @Test
    void manageStation() {
        // when
        createStation(STATION_NAME_KANGNAM);
        createStation(STATION_NAME_YEOKSAM);
        createStation(STATION_NAME_SEOLLEUNG);
        // then
        List<StationResponse> stations = getStations();
        assertThat(stations.size()).isEqualTo(3);

        // when
        deleteStation(stations.get(0).getId());
        // then
        List<StationResponse> stationsAfterDelete = getStations();
        assertThat(stationsAfterDelete.size()).isEqualTo(2);
    }

    List<StationResponse> getStations() {
        return
            given().when().
                get("/api/stations").
                then().
                log().all().
                extract().
                jsonPath().getList(".", StationResponse.class);
    }

    void deleteStation(Long id) {
        given().when().
            delete("/api/stations/" + id).
            then().
            log().all();
    }
}
