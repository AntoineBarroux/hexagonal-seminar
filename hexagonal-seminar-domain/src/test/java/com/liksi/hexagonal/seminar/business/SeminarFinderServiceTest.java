package com.liksi.hexagonal.seminar.business;

import com.liksi.hexagonal.seminar.ports.http.FakeAirlabsApiClient;
import com.liksi.hexagonal.seminar.ports.http.FakeClimatiqApiClient;
import com.liksi.hexagonal.seminar.ports.persistence.FakeSeminarRepository;

class SeminarFinderServiceTest {

    private SeminarFinderService seminarFinderService = new SeminarFinderService(new FakeAirlabsApiClient(), new FakeClimatiqApiClient(), new FakeSeminarRepository());


}