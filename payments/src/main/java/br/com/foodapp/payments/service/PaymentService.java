package br.com.foodapp.payments.service;


import br.com.foodapp.payments.dto.PaymentDTO;
import br.com.foodapp.payments.http.OrderClient;
import br.com.foodapp.payments.model.Payment;
import br.com.foodapp.payments.model.Status;
import br.com.foodapp.payments.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderClient pedido;

    public Page<PaymentDTO> getAll(Pageable pageable) {
        return repository
                .findAll(pageable)
                .map(p -> modelMapper.map(p, PaymentDTO.class));
    }

    public PaymentDTO getById(Long id){
        Payment payment = repository.findById(id).orElseThrow(() -> new EntityNotFoundException());

        return modelMapper.map(payment,PaymentDTO.class);
    }

    public PaymentDTO createPayment(PaymentDTO dto){
        Payment payment = modelMapper.map(dto,Payment.class);
        payment.setStatus(Status.CREATED);
        repository.save(payment);

        return modelMapper.map(payment,PaymentDTO.class);
    }

    public PaymentDTO updatePayment(Long id, PaymentDTO dto){
        Payment payment = modelMapper.map(dto,Payment.class);
        payment.setId(id);
        payment = repository.save(payment);
        return modelMapper.map(payment,PaymentDTO.class);
    }

    public void deletePayment(Long id){
        repository.deleteById(id);

    }

    public void confirmPayment(Long id){
        Optional<Payment> payment = repository.findById(id);

        if (!payment.isPresent()) {
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.CONFIRMED);
        repository.save(payment.get());
        pedido.updatePayment(payment.get().getOrderId());
    }

    public void updateStatus(Long id) {
        Optional<Payment> payment = repository.findById(id);

        if (!payment.isPresent()) {
            throw new EntityNotFoundException();
        }

        payment.get().setStatus(Status.CONFIRMED_NO_INTEGRATED);
        repository.save(payment.get());
    }
}
