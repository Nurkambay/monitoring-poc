INSERT INTO hikes.area (id, created_at, updated_at, name) SELECT id, createdAt, updatedAt, name from rightontrek.Areas;
INSERT INTO hikes.hike (id, name, hike_type, direction, start_date, end_date, created_at, updated_at, user_id, trail_id) SELECT id, name, hikeType, direction, startDate, endDate, createdAt, updatedAt, userId, trailId from rightontrek.Hikes;


INSERT INTO hikes.shape (id, created_at, updated_at, name) SELECT id, createdAt, updatedAt, name from rightontrek.Shapes;
INSERT INTO hikes.scope (id, created_at, updated_at, name) SELECT id, createdAt, updatedAt, name from rightontrek.Scopes;
INSERT INTO hikes.direction (id, created_at, updated_at, name) SELECT id, createdAt, updatedAt, name from rightontrek.Directions;
INSERT INTO hikes.trail (id, name, version, source, created_at, updated_at, scope_id, shape_id, parent_id) SELECT id, name, version, source, createdAt, updatedAt, scopeId, shapeId, parentId from rightontrek.Trails;
INSERT INTO hikes.trail_direction (trail_id, direction_id) SELECT TrailId, DirectionId from rightontrek.TrailDirections;
INSERT INTO hikes.waypoint (id, created_at, updated_at, elevation, latitude, longitude, trail_id) SELECT id, createdAt, updatedAt, elevation, latitude, longitude, trailId from rightontrek.Waypoints;
INSERT INTO hikes.package_type (id, created_at, updated_at, cost, dimensions, name) SELECT id, createdAt, updatedAt, cost, dimensions, name from rightontrek.PackageTypes;
INSERT INTO hikes.area (id, created_at, updated_at, name) SELECT id, createdAt, updatedAt, name from rightontrek.Areas;
INSERT INTO hikes.purpose (id, created_at, updated_at, name) SELECT id, createdAt, updatedAt, name from rightontrek.Purposes;
INSERT INTO hikes.poi (id, name, address, state, latitude, longitude, created_at, updated_at, trail_id, area_id, purpose_id) SELECT id, name, address, state, latitude, longitude, createdAt, updatedAt, trailId, areaId, purposeId from rightontrek.Pois;

INSERT INTO hikes.hike (id, name, hike_type, direction, start_date, end_date, created_at, updated_at, user_id, trail_id) SELECT id, name, hikeType, direction, startDate, endDate, createdAt, updatedAt, userIdm trailId from rightontrek.Hikes;



-- INSERT INTO hikes.waypoint (id, created_at, updated_at, elevation, latitude, longitude, trail_id) SELECT id, createdAt, updatedAt, elevation, latitude, longitude, trailId from rightontrek.Waypoints;


INSERT INTO hikes.waypoint_connections (waipoint_from, waipoint_to)  SELECT w1.id, min(w2.id) from rightontrek.Waypoints as w1  LEFT JOIN rightontrek.Waypoints as w2 ON w2.id > w1.id  GROUP BY w1.id;





