(defproject sencha "1.0.0"
  :description "Sample clojure application for CloudBees"

  ;; Simple CloudBees plugin config
  ;; :cloudbees-app-id "user/appname"
  ;; :cloudbees-api-key ~(slurp "~/MyKeys/api.key")
  ;; :cloudbees-api-secret ~(slurp "~/MyKeys/secret.key")

  :dependencies [[org.clojure/clojure "1.4.0"]
  		[compojure "1.1.1"]
                [org.clojure/java.jdbc "0.2.3"]
                [cheshire "4.0.1"]
		[com.h2database/h2 "1.3.168"]]
			
  :plugins [[lein-ring "0.7.1"]
	    [lein-cloudbees "1.0.1"]]
  
  :resources-path "../frontend/src/main/resources"

  :ring {:handler sencha.core/app
    :web-xml "../frontend/src/main/webapp/WEB-INF/web.xml"
    :init sencha.database/-main}
)