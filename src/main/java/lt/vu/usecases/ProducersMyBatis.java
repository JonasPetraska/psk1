package lt.vu.usecases;

import lombok.Getter;
import lombok.Setter;
import lt.vu.mybatis.model.Producer;
import lt.vu.interceptors.LoggedInvocation;
import lt.vu.mybatis.dao.ProducerMapper;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@Model
public class ProducersMyBatis {
    @Inject
    private ProducerMapper producerMapper;

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
        producerMapper.insert(producerToCreate);
        return "/mybatis/producers?faces-redirect=true";
    }

    private void loadAllProducers(){
        allProducers = producerMapper.selectAll();
    }
}
