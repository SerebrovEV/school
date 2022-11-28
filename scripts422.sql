CREATE TABLE car_user
(
    name          TEXT PRIMARY KEY,
    age           INTEGER CHECK ( age > 17 ),
    drive_license BOOLEAN DEFAULT 'false'
);

select *
from car_user
order by name;

CREATE TABLE car
(
    brand TEXT NOT NULL,
    model TEXT PRIMARY KEY,
    prise INTEGER CHECK ( prise > 0 )
);

select *
from car
order by model;

ALTER TABLE car_user ADD COLUMN car_model TEXT REFERENCES car(model);

select *
from car_user, car
where car_user.car_model=car.model;

SELECT u.name, u.age, c.model, c.brand, c.prise
FROM car_user u
FULL JOIN car c ON c.model=u.car_model;

