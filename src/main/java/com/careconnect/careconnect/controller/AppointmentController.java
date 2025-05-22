package com.careconnect.careconnect.controller;

import com.careconnect.careconnect.model.Appointment;
import com.careconnect.careconnect.repository.AppointmentRepository;
import com.careconnect.careconnect.repository.ClientRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    private final AppointmentRepository appointmentRepo;
    private final ClientRepository clientRepo;

    public AppointmentController(AppointmentRepository appointmentRepo, ClientRepository clientRepo) {
        this.appointmentRepo = appointmentRepo;
        this.clientRepo = clientRepo;
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentRepo.findAll();
    }

    @PostMapping
    public Appointment createAppointment(@RequestBody AppointmentRequest request) {
        var client = clientRepo.findById(request.clientId()).orElseThrow();
        Appointment appt = new Appointment();
        appt.setDate(request.date());
        appt.setNotes(request.notes());
        appt.setClient(client);
        return appointmentRepo.save(appt);
    }

    @PutMapping("/{id}")
    public Appointment updateAppointment(@PathVariable Long id, @RequestBody AppointmentRequest updated) {
        return appointmentRepo.findById(id).map(appt -> {
            var client = clientRepo.findById(updated.clientId()).orElseThrow();
            appt.setDate(updated.date());
            appt.setNotes(updated.notes());
            appt.setClient(client);
            return appointmentRepo.save(appt);
        }).orElseThrow(() -> new RuntimeException("Appointment not found with id " + id));
}

    // Inner record class for request body
    public record AppointmentRequest(Long clientId, java.time.LocalDate date, String notes) {}
}
