package com.maplinking.service;

import com.maplinking.maplink.MapLinkApi;
import com.maplinking.maplink.MapLinkException;
import com.maplinking.maplink.entity.Position;
import com.maplinking.maplink.entity.RouteSummary;
import com.maplinking.service.entity.LocationInfo;
import com.maplinking.service.entity.RouteInformation;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceImplTest {

    private static final LocationInfo LOCATION_1 = new LocationInfo("addr1", "123", "city1", "state1");
    private static final LocationInfo LOCATION_2 = new LocationInfo("addr2", "456", "city2", "state2");
    private static final LocationInfo LOCATION_3 = new LocationInfo("addr3", "789", "city3", "state3");

    private static final Position POSITION_1 = new Position("10.0", "10.0");
    private static final Position POSITION_2 = new Position("-20.0", "20.0");
    private static final Position POSITION_3 = new Position("30.0", "-30.0");

    private static final BigDecimal COST_1 = new BigDecimal(0.50);
    private static final BigDecimal COST_2 = new BigDecimal(1.00);

    private static final BigDecimal TEN = BigDecimal.TEN.setScale(2, BigDecimal.ROUND_UNNECESSARY);
    private static final BigDecimal TWENTY = BigDecimal.valueOf(20).setScale(2, BigDecimal.ROUND_UNNECESSARY);
    private static final BigDecimal SIXTY = BigDecimal.valueOf(60).setScale(2, BigDecimal.ROUND_UNNECESSARY);
    private static final BigDecimal SEVENTY = BigDecimal.valueOf(70).setScale(2, BigDecimal.ROUND_UNNECESSARY);

    @Mock
    private MapLinkApi mapLinkApiMock;

    @InjectMocks
    private RouteServiceImpl routeService;

    @Test
    public void successful_testTwoLocations() throws Exception {
        List<LocationInfo> locations = getLocationsList(LOCATION_1, LOCATION_2);
        List<Position> positions = getPositionsList(POSITION_1, POSITION_2);

        mockFindPosition(LOCATION_1, POSITION_1);
        mockFindPosition(LOCATION_2, POSITION_2);

        when(mapLinkApiMock.getRouteSummary(positions)).thenReturn(new RouteSummary(1000L, 20000D,
                BigDecimal.valueOf(50)));

        RouteInformation routeSummary = routeService.getRouteInformation(locations, COST_1);

        assertEquals("Wrong total time", Long.valueOf(1000L), routeSummary.getTotalTime());
        assertEquals("Wrong total distance", Double.valueOf(20000D), routeSummary.getTotalDistance());
        assertEquals("Wrong fueld cost", TEN, routeSummary.getFuelCost());
        assertEquals("Wrong total cost", SIXTY, routeSummary.getTotalCost());

        verifyFindPosition(LOCATION_1, 1);
        verifyFindPosition(LOCATION_2, 1);
        verifyFindPosition(LOCATION_3, 0);
        verify(mapLinkApiMock, times(1)).getRouteSummary(positions);
    }

    @Test
    public void successful_testTwoLocationsDifferentCost() throws Exception {
        List<LocationInfo> locations = getLocationsList(LOCATION_1, LOCATION_2);
        List<Position> positions = getPositionsList(POSITION_1, POSITION_2);

        mockFindPosition(LOCATION_1, POSITION_1);
        mockFindPosition(LOCATION_2, POSITION_2);

        when(mapLinkApiMock.getRouteSummary(positions)).thenReturn(new RouteSummary(1000L, 20000D,
                BigDecimal.valueOf(50)));

        RouteInformation routeSummary = routeService.getRouteInformation(locations, COST_2);

        assertEquals("Wrong total time", Long.valueOf(1000L), routeSummary.getTotalTime());
        assertEquals("Wrong total distance", Double.valueOf(20000D), routeSummary.getTotalDistance());
        assertEquals("Wrong fueld cost", TWENTY, routeSummary.getFuelCost());
        assertEquals("Wrong total cost", SEVENTY, routeSummary.getTotalCost());

        verifyFindPosition(LOCATION_1, 1);
        verifyFindPosition(LOCATION_2, 1);
        verifyFindPosition(LOCATION_3, 0);
        verify(mapLinkApiMock, times(1)).getRouteSummary(positions);
    }

    @Test
    public void successful_testThreeLocations() throws Exception {
        List<LocationInfo> locations = getLocationsList(LOCATION_1, LOCATION_2, LOCATION_3);
        List<Position> positions = getPositionsList(POSITION_1, POSITION_2, POSITION_3);

        mockFindPosition(LOCATION_1, POSITION_1);
        mockFindPosition(LOCATION_2, POSITION_2);
        mockFindPosition(LOCATION_3, POSITION_3);

        when(mapLinkApiMock.getRouteSummary(positions)).thenReturn(new RouteSummary(1000L, 20000D,
                BigDecimal.valueOf(50)));

        RouteInformation routeSummary = routeService.getRouteInformation(locations, COST_1);

        assertEquals("Wrong total time", Long.valueOf(1000L), routeSummary.getTotalTime());
        assertEquals("Wrong total distance", Double.valueOf(20000D), routeSummary.getTotalDistance());
        assertEquals("Wrong fueld cost", TEN, routeSummary.getFuelCost());
        assertEquals("Wrong total cost", SIXTY, routeSummary.getTotalCost());

        verifyFindPosition(LOCATION_1, 1);
        verifyFindPosition(LOCATION_2, 1);
        verifyFindPosition(LOCATION_3, 1);
        verify(mapLinkApiMock, times(1)).getRouteSummary(positions);
    }

    @Test(expected = ServiceException.class)
    public void unsuccessful_serviceException() throws Exception {
        List<LocationInfo> locations = getLocationsList(LOCATION_1, LOCATION_2);
        List<Position> positions = getPositionsList(POSITION_1, POSITION_2);

        mockFindPosition(LOCATION_1, POSITION_1);
        mockFindPosition(LOCATION_2, POSITION_2);

        when(mapLinkApiMock.getRouteSummary(positions)).thenThrow(new MapLinkException("TestException"));

        try {
            routeService.getRouteInformation(locations, COST_1);
        } catch (ServiceException e) {
            assertEquals("Wrong error message", "TestException", e.getMessage());
            verifyFindPosition(LOCATION_1, 1);
            verifyFindPosition(LOCATION_2, 1);
            verifyFindPosition(LOCATION_3, 0);
            verify(mapLinkApiMock, times(1)).getRouteSummary(positions);
            throw e;
        }
    }

    @Test(expected = RuntimeException.class)
    public void unsuccessful_runtimeException() throws Exception {
        List<LocationInfo> locations = getLocationsList(LOCATION_1, LOCATION_2);
        List<Position> positions = getPositionsList(POSITION_1, POSITION_2);

        mockFindPosition(LOCATION_1, POSITION_1);
        mockFindPosition(LOCATION_2, POSITION_2);

        when(mapLinkApiMock.getRouteSummary(positions)).thenThrow(new RuntimeException("RuntimeException"));

        try {
            routeService.getRouteInformation(locations, COST_1);
        } catch (RuntimeException e) {
            assertEquals("Wrong error message", "RuntimeException", e.getMessage());
            verifyFindPosition(LOCATION_1, 1);
            verifyFindPosition(LOCATION_2, 1);
            verifyFindPosition(LOCATION_3, 0);
            verify(mapLinkApiMock, times(1)).getRouteSummary(positions);
            throw e;
        }
    }

    private void mockFindPosition(LocationInfo locationInfo, Position position) throws MapLinkException {
        when(mapLinkApiMock.findPosition(locationInfo.getAddress(), locationInfo.getNumber(), locationInfo.getCity(),
                locationInfo.getState())).thenReturn(position);
    }

    private void verifyFindPosition(LocationInfo locationInfo, int times) throws MapLinkException {
        verify(mapLinkApiMock, times(times)).findPosition(locationInfo.getAddress(), locationInfo.getNumber(),
                locationInfo.getCity(), locationInfo.getState());
    }

    private List<LocationInfo> getLocationsList(LocationInfo... locations) {
        ArrayList<LocationInfo> list = new ArrayList<>();
        Collections.addAll(list, locations);
        return list;
    }

    private List<Position> getPositionsList(Position... positions) {
        ArrayList<Position> list = new ArrayList<>();
        Collections.addAll(list, positions);
        return list;
    }

}
