DROP TABLE IF EXISTS avg_time_view;
DROP TABLE IF EXISTS rental_history_view;

CREATE VIEW IF NOT EXISTS avg_time_view AS (SELECT rp.id rp_id, rp.name rp_name, vt.id vt_id, vt.name vt_name, vm.id vm_id, vm.name vm_name, AVG(DATEDIFF(HOUR, vr.begin_rent_time, vr.end_rent_time)) avg_time FROM vehicle_rented vr JOIN rental_point rp ON vr.begin_rental_point_id = rp.id JOIN vehicle v ON vr.vehicle_id = v.id JOIN vehicle_model vm ON v.vehicle_model_id = vm.id JOIN vehicle_type vt ON vm.vehicle_type_id = vt.id WHERE vr.begin_rent_time IS NOT NULL AND vr.end_rent_time IS NOT NULL GROUP BY rp.id, rp.name, vt.id, vt.name, vm.id, vm.name);
--CREATE VIEW IF NOT EXISTS rental_history_view AS (SELECT vr.id rental_id, vm.vehicle_type_id,  v.vehicle_model_id, vr.customer_id, vt.name type_name, vm.name model_name, v.reg_number, c.name customer_name, vr.begin_rent_time, vr.end_rent_time FROM vehicle_rented vr JOIN vehicle v ON vr.vehicle_id = v.id JOIN vehicle_model vm ON v.vehicle_model_id = vm.id JOIN vehicle_type vt ON vm.vehicle_type_id = vt.id JOIN customer c ON vr.customer_id = c.id WHERE vr.begin_rent_time IS NOT NULL);
CREATE VIEW IF NOT EXISTS rental_history_view AS (SELECT vr.id rental_id, vm.vehicle_type_id,  v.vehicle_model_id, vr.customer_id, vt.name type_name, vm.name model_name, v.reg_number, c.name customer_name, vr.begin_rent_time, vr.end_rent_time, substr(listagg(formatdatetime(vrh.event_time,'dd.MM.yyyy HH:mm:ss')||'   '||vrh.location,';') WITHIN GROUP (ORDER BY vrh.id DESC),1 ,500) history FROM vehicle_rented vr JOIN vehicle v ON vr.vehicle_id = v.id JOIN vehicle_model vm ON v.vehicle_model_id = vm.id JOIN vehicle_type vt ON vm.vehicle_type_id = vt.id JOIN customer c ON vr.customer_id = c.id JOIN vehicle_rent_history vrh ON vrh.vehicle_rented_id = vr.id WHERE vr.begin_rent_time IS NOT NULL GROUP BY vr.id, vm.vehicle_type_id,  v.vehicle_model_id, vr.customer_id, vt.name, vm.name, v.reg_number, c.name, vr.begin_rent_time, vr.end_rent_time);




