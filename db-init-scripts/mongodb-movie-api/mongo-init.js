db = db.getSiblingDB('movie-data')

db.getCollection("categories").insertOne({ref: "45b2dd57-e985-411c-aa67-1f4981f1b6cf", name: "Action"});
db.getCollection("categories").insertOne({ref: "605f08b0-0166-4457-8481-08a3c44fe1ec", name: "Thriller"});
db.getCollection("categories").insertOne({ref: "f13603d6-21e4-4ba3-8678-9f518dabd67c", name: "Drama"});
db.getCollection("categories").insertOne({ref: "47828dc2-e0cb-4bd1-ba7a-c165c0f6a760", name: "Romance"});


db.getCollection("movie-tracking-actions").insertOne({ref: "43339c83-f83a-4555-9d7d-636c2d5f41fd", name: "COMPLETED"});
db.getCollection("movie-tracking-actions").insertOne({ref: "3121d1ba-f1ad-4a2e-a66f-e0e4143ab929", name: "STOPPED_BEFORE_HALF"});
db.getCollection("movie-tracking-actions").insertOne({ref: "54c52665-1431-47da-9c00-94119739ab1e", name: "STOPPED_BEFORE_END"});
db.getCollection("movie-tracking-actions").insertOne({ref: "b7b31abb-ddae-4f61-af95-9d95b5ad3d8a", name: "RESUMED"});
db.getCollection("movie-tracking-actions").insertOne({ref: "b82f3c9a-932c-45f8-b169-6870b9ce957c", name: "LIKED"});
db.getCollection("movie-tracking-actions").insertOne({ref: "f44505c2-354f-4b2d-802d-93527519662f", name: "DISLIKED"});


db.getCollection("movies").insertOne({ref: "ea86111a-3b05-483d-9651-dcb1e76850ea", name: "An impressive movie I", actors: ["13ed6a67-a4c4-4307-85da-2accbcf25aa7", "23ed6a67-a4c4-4307-85da-2accbcf25aa7"], categories: ["45b2dd57-e985-411c-aa67-1f4981f1b6cf", "f13603d6-21e4-4ba3-8678-9f518dabd67c"], created: "2023-06-12T09:33:17.674336"});
db.getCollection("movies").insertOne({ref: "54b7e51b-9c27-4258-a167-2dbc04b0ee5c", name: "An impressive movie II", actors: ["13ed6a67-a4c4-4307-85da-2accbcf25aa7", "33ed6a67-a4c4-4307-85da-2accbcf25aa7"], categories: ["45b2dd57-e985-411c-aa67-1f4981f1b6cf", "f13603d6-21e4-4ba3-8678-9f518dabd67c"], created: "2023-06-12T09:33:18.674336"});
db.getCollection("movies").insertOne({ref: "bd242106-ced9-42ad-8097-e3723a4b2bb7", name: "An impressive movie III", actors: ["13ed6a67-a4c4-4307-85da-2accbcf25aa7", "23ed6a67-a4c4-4307-85da-2accbcf25aa7", "33ed6a67-a4c4-4307-85da-2accbcf25aa7"], categories: ["45b2dd57-e985-411c-aa67-1f4981f1b6cf", "f13603d6-21e4-4ba3-8678-9f518dabd67c"], created: "2023-06-12T09:33:19.674336"});

db.getCollection("movie-tracking-records").insertOne({ref: "d8cbbdf4-21f1-4dcf-b18d-2ffb9f7962ef", person: "13ed6a67-a4c4-4307-85da-2accbcf25aa7", movie: "ea86111a-3b05-483d-9651-dcb1e76850ea", action: "43339c83-f83a-4555-9d7d-636c2d5f41fd", created: "2023-06-12T09:33:12.674336"});
db.getCollection("movie-tracking-records").insertOne({ref: "8fb99d64-ba10-4cb0-af74-e3750b1d4a5f", person: "13ed6a67-a4c4-4307-85da-2accbcf25aa7", movie: "54b7e51b-9c27-4258-a167-2dbc04b0ee5c", action: "3121d1ba-f1ad-4a2e-a66f-e0e4143ab929", created: "2023-06-12T09:33:13.674336"});
db.getCollection("movie-tracking-records").insertOne({ref: "cb9a40c7-3881-429d-a47f-fd5ae06eab80", person: "13ed6a67-a4c4-4307-85da-2accbcf25aa7", movie: "54b7e51b-9c27-4258-a167-2dbc04b0ee5c", action: "b7b31abb-ddae-4f61-af95-9d95b5ad3d8a", created: "2023-06-12T09:33:14.674336"});
db.getCollection("movie-tracking-records").insertOne({ref: "19ba4de4-cce5-47f4-8552-dcdf4eb6f7b1", person: "13ed6a67-a4c4-4307-85da-2accbcf25aa7", movie: "54b7e51b-9c27-4258-a167-2dbc04b0ee5c", action: "43339c83-f83a-4555-9d7d-636c2d5f41fd", created: "2023-06-12T09:33:15.674336"});
db.getCollection("movie-tracking-records").insertOne({ref: "8445b46b-e40f-491a-a8b7-67df1ac73720", person: "13ed6a67-a4c4-4307-85da-2accbcf25aa7", movie: "54b7e51b-9c27-4258-a167-2dbc04b0ee5c", action: "b82f3c9a-932c-45f8-b169-6870b9ce957c", created: "2023-06-12T09:33:16.674336"});
db.getCollection("movie-tracking-records").insertOne({ref: "07630742-9ee5-4b71-9e08-bd609815c6d9", person: "13ed6a67-a4c4-4307-85da-2accbcf25aa7", movie: "bd242106-ced9-42ad-8097-e3723a4b2bb7", action: "f44505c2-354f-4b2d-802d-93527519662f", created: "2023-06-12T09:33:17.674336"});

db.getCollection("people").insertOne({ref: "13ed6a67-a4c4-4307-85da-2accbcf25aa7", name: "Maarten Vandeperre"});
db.getCollection("people").insertOne({ref: "23ed6a67-a4c4-4307-85da-2accbcf25aa7", name: "Pieter Vandeperre"});
db.getCollection("people").insertOne({ref: "33ed6a67-a4c4-4307-85da-2accbcf25aa7", name: "Bart Joris"});




























