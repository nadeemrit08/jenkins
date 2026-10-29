package com.rapido.repository;

import org.springframework.stereotype.Repository;

import com.rapido.entity.BookingEntity;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

@Repository
@Transactional
public class BookingRepository {

	@PersistenceContext
	private EntityManager entityManager;

	public int createBooking(String mobile, String src, String desc, String type, String amount) {

		System.out.println("BookingRepository.createBooking()");

		BookingEntity bookingEntity = new BookingEntity();

		bookingEntity.setAmount(amount);
		bookingEntity.setDestination(desc);
		bookingEntity.setRideType(type);
		bookingEntity.setSource(src);
		bookingEntity.setMobile(mobile);

		entityManager.persist(bookingEntity);

		if (bookingEntity.getId() > 0) {
			return bookingEntity.getId();
		}

		return 0;
	}

}
