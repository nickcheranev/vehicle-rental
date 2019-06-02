SELECT vr.id rental_id, vm.vehicle_type_id,  v.vehicle_model_id, vr.customer_id, vt.name type_name, vm.name model_name, v.reg_number, c.name customer_name, vr.begin_rent_time, vr.end_rent_time, listagg(formatdatetime(vrh.event_time,'dd.MM,yyyy HH:mm:ss')||' '||vrh.location,';') within group (order by vrh.event_time) history
FROM vehicle_rented vr JOIN vehicle v ON vr.vehicle_id = v.id
  JOIN vehicle_model vm ON v.vehicle_model_id = vm.id
  JOIN vehicle_type vt ON vm.vehicle_type_id = vt.id
  JOIN customer c ON vr.customer_id = c.id
  JOIN vehicle_rent_history vrh ON vrh.vehicle_rented_id = vr.id
WHERE vr.begin_rent_time IS NOT NULL
GROUP BY vr.id, vm.vehicle_type_id,  v.vehicle_model_id, vr.customer_id, vt.name, vm.name, v.reg_number, c.name, vr.begin_rent_time, vr.end_rent_time
