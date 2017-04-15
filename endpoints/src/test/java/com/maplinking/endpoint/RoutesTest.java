package com.maplinking.endpoint;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.maplinking.endpoint.entity.AddressJson;
import com.maplinking.endpoint.entity.RouteJson;
import com.maplinking.service.RouteService;
import com.maplinking.service.entity.RouteInformation;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(MockitoJUnitRunner.class)
public class RoutesTest {

    private static final AddressJson ADDRESS_1 = new AddressJson("addr1", "123", "city1", "state1");
    private static final AddressJson ADDRESS_2 = new AddressJson("addr2", "456", "city2", "state2");
    private static final AddressJson[] ADDRESS_ARR = {ADDRESS_1, ADDRESS_2};

    private static final ObjectMapper MAPPER = new ObjectMapper();

    private static final List<AddressJson> ADDRESSES = Arrays.asList(ADDRESS_ARR);

    private static final BigDecimal FUEL_COST = BigDecimal.valueOf(50).setScale(2, BigDecimal.ROUND_UNNECESSARY);
    private static final BigDecimal TOTAL_COST = BigDecimal.valueOf(80).setScale(2, BigDecimal.ROUND_UNNECESSARY);

    private static final BigDecimal DEFAULT_FUELD_COST_PER_KM = new BigDecimal("0.25");
    private static final BigDecimal DIFFERENT_FUELD_COST_PER_KM = new BigDecimal("0.50");

    @Mock
    private RouteService routeService;

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private Validator validator;

    @InjectMocks
    private Routes routes;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(routes).build();
    }

    @Test
    public void postDefaultCostPerKm() throws Exception {
        RouteInformation routeInformation = new RouteInformation(1000L, 20000D, FUEL_COST, TOTAL_COST);

        when(routeService.getRouteInformation(anyList(), eq(DEFAULT_FUELD_COST_PER_KM))).thenReturn(routeInformation);

        mockMvc.perform(
                post("/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(ADDRESSES))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(MAPPER.writeValueAsString(new RouteJson(routeInformation))));

        verify(validator, times(1)).validate(anyList());
        verify(routeService, times(1)).getRouteInformation(anyList(), eq(DEFAULT_FUELD_COST_PER_KM));
    }

    @Test
    public void postWithDifferentCostPerKm() throws Exception {
        RouteInformation routeInformation = new RouteInformation(1000L, 20000D, FUEL_COST, TOTAL_COST);

        when(routeService.getRouteInformation(anyList(), eq(DIFFERENT_FUELD_COST_PER_KM))).thenReturn(routeInformation);

        mockMvc.perform(
                post("/routes")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(MAPPER.writeValueAsString(ADDRESSES))
                        .accept(MediaType.APPLICATION_JSON)
                        .param("costPerKm", "0.50"))
                .andExpect(status().isOk())
                .andExpect(content().string(MAPPER.writeValueAsString(new RouteJson(routeInformation))));

        verify(validator, times(1)).validate(anyList());
        verify(routeService, times(1)).getRouteInformation(anyList(), eq(DIFFERENT_FUELD_COST_PER_KM));
    }

    @Test
    public void getDefaultCostPerKm() throws Exception {
        RouteInformation routeInformation = new RouteInformation(1000L, 20000D, FUEL_COST, TOTAL_COST);

        String requestJson = MAPPER.writeValueAsString(ADDRESSES);

        when(routeService.getRouteInformation(anyList(), eq(DEFAULT_FUELD_COST_PER_KM))).thenReturn(routeInformation);
        when(objectMapper.readValue(eq(requestJson), any(TypeReference.class))).thenReturn(ADDRESSES);

        mockMvc.perform(
                get("/routes/{addresses}", requestJson)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(MAPPER.writeValueAsString(new RouteJson(routeInformation))));

        verify(validator, times(1)).validate(anyList());
        verify(routeService, times(1)).getRouteInformation(anyList(), eq(DEFAULT_FUELD_COST_PER_KM));
        verify(objectMapper, times(1)).readValue(eq(requestJson), any(TypeReference.class));
    }

    @Test
    public void getWithDifferentCostPerKm() throws Exception {
        RouteInformation routeInformation = new RouteInformation(1000L, 20000D, FUEL_COST, TOTAL_COST);

        String requestJson = MAPPER.writeValueAsString(ADDRESSES);

        when(routeService.getRouteInformation(anyList(), eq(DIFFERENT_FUELD_COST_PER_KM))).thenReturn(routeInformation);
        when(objectMapper.readValue(eq(requestJson), any(TypeReference.class))).thenReturn(ADDRESSES);

        mockMvc.perform(
                get("/routes/{addresses}", requestJson)
                        .accept(MediaType.APPLICATION_JSON)
                        .param("costPerKm", "0.50"))
                .andExpect(status().isOk())
                .andExpect(content().string(MAPPER.writeValueAsString(new RouteJson(routeInformation))));

        verify(validator, times(1)).validate(anyList());
        verify(routeService, times(1)).getRouteInformation(anyList(), eq(DIFFERENT_FUELD_COST_PER_KM));
        verify(objectMapper, times(1)).readValue(eq(requestJson), any(TypeReference.class));
    }
}
