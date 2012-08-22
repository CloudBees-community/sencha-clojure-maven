(ns sencha.core
  (:use sencha.database)
  (:use compojure.core)
  (:use ring.middleware.resource)
  (:use ring.util.response)
  (:use cheshire.core)
  (:require 
	  [compojure.route :as route]
	  [compojure.handler :as handler]
    [clojure.java.jdbc :as sql]))

(defn add-contact [contact info]
  (let [req "insert into contacts (contact, info) values (? , ?)"] 
    (sql/with-connection db
      (sql/do-prepared req [contact info]))))

(defn delete-contact [id]
  (sql/with-connection db
    (sql/delete-rows :contacts ["id=?" id])))

(defn update-contact [id contact info]
  (sql/with-connection db 
    (sql/update-values 
      :contacts
      ["id=?" id] 
      {:contact contact
      :info info})))

(defn view-contacts []
  (sql/with-connection db
    (sql/with-query-results rs ["select * from contacts"]
       (generate-string {:contacts rs}))))

(defroutes main-routes
  (GET "/" []
    (resource-response "index.html" {:root "public"}))
  (GET "/contactList" []
    (view-contacts))
  (POST "/addContact" [contact info] 
    (add-contact contact info))
  (POST "/deleteContact" [cid] 
    (delete-contact cid))
  (POST "/updateContact" [cid contact info] 
    (update-contact cid contact info))
  (route/resources "/")
  (route/not-found "Not Found"))

(def app
  (handler/site main-routes))