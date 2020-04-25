package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.entities.Producer;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.persistence.ProducersDAO;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class Producers {
    @Inject
    private ProducersDAO producersDAO;

    @Getter
    @Setter
    private Producer producerToCreate = new Producer();

    @Getter
    private List<Producer> allProducers;

    @PostConstruct
    public void init(){
        loadAllProducers();
    }

    @Transactional
    @LoggedInvocation
    public String createProducer(){
        producersDAO.persist(producerToCreate);
        return "producers?faces-redirect=true";
    }

    private void loadAllProducers(){
        allProducers = producersDAO.getAllProducers();
    }
}
