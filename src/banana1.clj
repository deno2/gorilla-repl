;; gorilla-repl.fileformat = 1

;; **
;;; # An Exploration of the Gorilla REPL
;;; 
;;; This is my first play around with Gorilla REPL. I'm going to talk about `clojure.data/diff` as an example.
;;; 
;;; This might even turn out to be my first actual blog post!!
;;; 
;;; **So how do I use this big, hairy REPL?**
;;; 
;;; First define a namespace and specify your requirements as usual:
;; **

;; @@
(ns worksheets.banana1
  (:require [gorilla-plot.core :as plot]
            [gorilla-repl.table :as table])
  (:use clojure.repl
        clojure.pprint
        clojure.data))
;; @@
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; **
;;; ### Have you heard about clojure.data/diff?
;; **

;; @@
(doc diff)
;; @@
;; ->
;;; -------------------------
;;; clojure.data/diff
;;; ([a b])
;;;   Recursively compares a and b, returning a tuple of
;;;   [things-only-in-a things-only-in-b things-in-both].
;;;   Comparison rules:
;;; 
;;;   * For equal a and b, return [nil nil a].
;;;   * Maps are subdiffed where keys match and values differ.
;;;   * Sets are never subdiffed.
;;;   * All sequential things are treated as associative collections
;;;     by their indexes, with results returned as vectors.
;;;   * Everything else (including strings!) is treated as
;;;     an atom and compared for equality.
;;; 
;; <-
;; =>
;;; {"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}
;; <=

;; @@
(diff [1 2] [2 3])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[1 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[2 3]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[[1 2] [2 3] nil]"}
;; <=

;; **
;;; The result: `[[1 2] [2 3] nil]` means that `[1 2]` are only in the first data vector, `[2 3]` are only in the second vector and the `nil` means that no values exist in both of the input parameters.
;;; 
;;; **_This is clearly quite wrong!_**
;;; 
;;; 
;; **

;; **
;;; While we're here lets try out another of the features of Gorilla REPL. We'll create a few more examples of using `diff` and plot them using `gorilla-repl.table/table-view` to see if this gives us a clearer picture...
;; **

;; @@
(table/table-view (for [x (range 1 3)
                        y (range 1 3)]
                    (let [a [x y]
                          b [y x]
                          result (diff a b)]
                      [a b (first result) (second result) (last result)])) 
                  :columns ["a" "b" "only in a" "only in b" "in both"])
;; @@
;; =>
;;; {"type":"list-like","open":"<center><table>","close":"</table></center>","separator":"\n","items":[{"type":"list-like","open":"<tr><th>","close":"</th></tr>","separator":"</th><th>","items":[{"type":"html","content":"<span class='clj-string'>&quot;a&quot;</span>","value":"\"a\""},{"type":"html","content":"<span class='clj-string'>&quot;b&quot;</span>","value":"\"b\""},{"type":"html","content":"<span class='clj-string'>&quot;only in a&quot;</span>","value":"\"only in a\""},{"type":"html","content":"<span class='clj-string'>&quot;only in b&quot;</span>","value":"\"only in b\""},{"type":"html","content":"<span class='clj-string'>&quot;in both&quot;</span>","value":"\"in both\""}],"value":"[\"a\" \"b\" \"only in a\" \"only in b\" \"in both\"]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[1 1]"}],"value":"[[1 1] [1 1] nil nil [1 1]]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[1 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[2 1]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[1 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[2 1]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[[1 2] [2 1] [1 2] [2 1] nil]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[2 1]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[1 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"[2 1]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[1 2]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"}],"value":"[[2 1] [1 2] [2 1] [1 2] nil]"},{"type":"list-like","open":"<tr><td>","close":"</td></tr>","separator":"</td><td>","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[2 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[2 2]"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[2 2]"}],"value":"[[2 2] [2 2] nil nil [2 2]]"}],"value":"#gorilla_repl.table.TableView{:contents ([[1 1] [1 1] nil nil [1 1]] [[1 2] [2 1] [1 2] [2 1] nil] [[2 1] [1 2] [2 1] [1 2] nil] [[2 2] [2 2] nil nil [2 2]]), :opts (:columns [\"a\" \"b\" \"only in a\" \"only in b\" \"in both\"])}"}
;; <=

;; **
;;; **_So what went wrong?_**
;;; 
;;; You were probably expecting something like this:
;;; 
;;; Input: `(diff [1 2] [2 3])`
;;; 
;;; Output: `[[1] [3] [2]]`
;;; 
;;; Well the answer is that nothing is wrong. It was just a bit of a trick question to generate some content! Ha Ha! `diff` compares two *_values_* and a vector _is_ a value made up of a number of values in a specific order (or at a specific index).
;;; 
;;; To produce the 'correct' answer from above we can compare two sets:
;; **

;; @@
(clojure.data/diff #{1 2} #{2 3})
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-set'>#{</span>","close":"<span class='clj-set'>}</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"}],"value":"#{1}"},{"type":"list-like","open":"<span class='clj-set'>#{</span>","close":"<span class='clj-set'>}</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"#{3}"},{"type":"list-like","open":"<span class='clj-set'>#{</span>","close":"<span class='clj-set'>}</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"#{2}"}],"value":"[#{1} #{3} #{2}]"}
;; <=

;; **
;;; This is a better example of comparing two vectors:
;; **

;; @@
(clojure.data/diff [1 2 2] [1 2 3])
;; @@
;; =>
;;; {"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[nil nil 2]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-nil'>nil</span>","value":"nil"},{"type":"html","content":"<span class='clj-long'>3</span>","value":"3"}],"value":"[nil nil 3]"},{"type":"list-like","open":"<span class='clj-vector'>[</span>","close":"<span class='clj-vector'>]</span>","separator":" ","items":[{"type":"html","content":"<span class='clj-long'>1</span>","value":"1"},{"type":"html","content":"<span class='clj-long'>2</span>","value":"2"}],"value":"[1 2]"}],"value":"[[nil nil 2] [nil nil 3] [1 2]]"}
;; <=

;; **
;;; It shows that the first two elements of the vectors are the same in both. It is only the third item that is different; it's 2 in a, and 3 in b.
;;; 
;;; ### So Enough of this BS. What do I think of Gorilla REPL so far?
;;; 
;;; It's not quite Mathematica but that's not the point. I think it's forking awsome and it makes my mouth go like this:
;; **

;; @@
(plot/plot (fn [x] (* x x)) [-5 5])
;; @@
;; =>
;;; {"type":"vega","content":{"axes":[{"scale":"x","type":"x"},{"scale":"y","type":"y"}],"scales":[{"name":"x","type":"linear","range":"width","zero":false,"domain":{"data":"dac89a5b-d008-42db-8032-cd9c0d144470","field":"data.x"}},{"name":"y","type":"linear","range":"height","nice":true,"zero":false,"domain":{"data":"dac89a5b-d008-42db-8032-cd9c0d144470","field":"data.y"}}],"marks":[{"type":"line","from":{"data":"dac89a5b-d008-42db-8032-cd9c0d144470"},"properties":{"enter":{"x":{"scale":"x","field":"data.x"},"y":{"scale":"y","field":"data.y"},"stroke":{"value":"#FF29D2"},"strokeWidth":{"value":2},"strokeOpacity":{"value":1}}}}],"data":[{"name":"dac89a5b-d008-42db-8032-cd9c0d144470","values":[{"x":-5,"y":25},{"x":-4.899999998509884,"y":24.009999985396863},{"x":-4.799999997019768,"y":23.039999971389772},{"x":-4.699999995529652,"y":22.089999957978726},{"x":-4.5999999940395355,"y":21.159999945163726},{"x":-4.499999992549419,"y":20.249999932944775},{"x":-4.399999991059303,"y":19.35999992132187},{"x":-4.299999989569187,"y":18.48999991029501},{"x":-4.199999988079071,"y":17.639999899864197},{"x":-4.099999986588955,"y":16.80999989002943},{"x":-3.999999985098839,"y":15.99999988079071},{"x":-3.8999999836087227,"y":15.209999872148037},{"x":-3.7999999821186066,"y":14.43999986410141},{"x":-3.6999999806284904,"y":13.68999985665083},{"x":-3.5999999791383743,"y":12.959999849796295},{"x":-3.499999977648258,"y":12.249999843537807},{"x":-3.399999976158142,"y":11.559999837875367},{"x":-3.299999974668026,"y":10.889999832808972},{"x":-3.19999997317791,"y":10.239999828338624},{"x":-3.0999999716877937,"y":9.609999824464323},{"x":-2.9999999701976776,"y":8.999999821186066},{"x":-2.8999999687075615,"y":8.409999818503858},{"x":-2.7999999672174454,"y":7.839999816417695},{"x":-2.6999999657273293,"y":7.2899998149275795},{"x":-2.599999964237213,"y":6.75999981403351},{"x":-2.499999962747097,"y":6.249999813735487},{"x":-2.399999961256981,"y":5.75999981403351},{"x":-2.2999999597668648,"y":5.2899998149275795},{"x":-2.1999999582767487,"y":4.839999816417696},{"x":-2.0999999567866325,"y":4.4099998185038585},{"x":-1.9999999552965164,"y":3.9999998211860675},{"x":-1.8999999538064003,"y":3.609999824464323},{"x":-1.7999999523162842,"y":3.2399998283386253},{"x":-1.699999950826168,"y":2.8899998328089738},{"x":-1.599999949336052,"y":2.5599998378753686},{"x":-1.4999999478459358,"y":2.24999984353781},{"x":-1.3999999463558197,"y":1.959999849796298},{"x":-1.2999999448657036,"y":1.6899998566508323},{"x":-1.1999999433755875,"y":1.4399998641014131},{"x":-1.0999999418854713,"y":1.2099998721480403},{"x":-0.9999999403953552,"y":0.999999880790714},{"x":-0.8999999389052391,"y":0.8099998900294341},{"x":-0.799999937415123,"y":0.6399998998642007},{"x":-0.6999999359250069,"y":0.4899999102950137},{"x":-0.5999999344348907,"y":0.3599999213218732},{"x":-0.4999999329447746,"y":0.24999993294477912},{"x":-0.3999999314546585,"y":0.1599999451637315},{"x":-0.2999999299645424,"y":0.08999995797873034},{"x":-0.19999992847442627,"y":0.039999971389775624},{"x":-0.09999992698431015,"y":0.009999985396867361},{"x":7.450580596923828E-8,"y":5.551115123125783E-15},{"x":0.10000007599592209,"y":0.010000015199190193},{"x":0.2000000774860382,"y":0.04000003099442129},{"x":0.3000000789761543,"y":0.09000004738569883},{"x":0.40000008046627045,"y":0.16000006437302283},{"x":0.5000000819563866,"y":0.2500000819563933},{"x":0.6000000834465027,"y":0.3600001001358102},{"x":0.7000000849366188,"y":0.49000011891127354},{"x":0.8000000864267349,"y":0.6400001382827833},{"x":0.900000087916851,"y":0.8100001582503396},{"x":1.0000000894069672,"y":1.0000001788139423},{"x":1.1000000908970833,"y":1.2100001999735914},{"x":1.2000000923871994,"y":1.440000221729287},{"x":1.3000000938773155,"y":1.6900002440810291},{"x":1.4000000953674316,"y":1.9600002670288177},{"x":1.5000000968575478,"y":2.2500002905726526},{"x":1.6000000983476639,"y":2.560000314712534},{"x":1.70000009983778,"y":2.890000339448462},{"x":1.8000001013278961,"y":3.2400003647804363},{"x":1.9000001028180122,"y":3.610000390708457},{"x":2.0000001043081284,"y":4.000000417232524},{"x":2.1000001057982445,"y":4.410000444352638},{"x":2.2000001072883606,"y":4.840000472068798},{"x":2.3000001087784767,"y":5.290000500381005},{"x":2.400000110268593,"y":5.7600005292892575},{"x":2.500000111758709,"y":6.250000558793557},{"x":2.600000113248825,"y":6.760000588893903},{"x":2.700000114738941,"y":7.2900006195902955},{"x":2.8000001162290573,"y":7.840000650882734},{"x":2.9000001177191734,"y":8.41000068277122},{"x":3.0000001192092896,"y":9.000000715255752},{"x":3.1000001206994057,"y":9.61000074833633},{"x":3.200000122189522,"y":10.240000782012954},{"x":3.300000123679638,"y":10.890000816285626},{"x":3.400000125169754,"y":11.560000851154342},{"x":3.50000012665987,"y":12.250000886619107},{"x":3.6000001281499863,"y":12.960000922679917},{"x":3.7000001296401024,"y":13.690000959336775},{"x":3.8000001311302185,"y":14.440000996589678},{"x":3.9000001326203346,"y":15.210001034438628},{"x":4.000000134110451,"y":16.000001072883624},{"x":4.100000135600567,"y":16.810001111924667},{"x":4.200000137090683,"y":17.640001151561755},{"x":4.300000138580799,"y":18.490001191794892},{"x":4.400000140070915,"y":19.360001232624075},{"x":4.500000141561031,"y":20.250001274049303},{"x":4.6000001430511475,"y":21.160001316070577},{"x":4.700000144541264,"y":22.0900013586879},{"x":4.80000014603138,"y":23.040001401901268},{"x":4.900000147521496,"y":24.01000144571068}]}],"width":400,"height":247.2187957763672,"padding":{"bottom":20,"top":10,"right":10,"left":50}},"value":"#gorilla_repl.vega.VegaView{:content {:axes [{:scale \"x\", :type \"x\"} {:scale \"y\", :type \"y\"}], :scales [{:name \"x\", :type \"linear\", :range \"width\", :zero false, :domain {:data \"dac89a5b-d008-42db-8032-cd9c0d144470\", :field \"data.x\"}} {:name \"y\", :type \"linear\", :range \"height\", :nice true, :zero false, :domain {:data \"dac89a5b-d008-42db-8032-cd9c0d144470\", :field \"data.y\"}}], :marks [{:type \"line\", :from {:data \"dac89a5b-d008-42db-8032-cd9c0d144470\"}, :properties {:enter {:x {:scale \"x\", :field \"data.x\"}, :y {:scale \"y\", :field \"data.y\"}, :stroke {:value \"#FF29D2\"}, :strokeWidth {:value 2}, :strokeOpacity {:value 1}}}}], :data [{:name \"dac89a5b-d008-42db-8032-cd9c0d144470\", :values ({:x -5, :y 25} {:x -4.899999998509884, :y 24.009999985396863} {:x -4.799999997019768, :y 23.039999971389772} {:x -4.699999995529652, :y 22.089999957978726} {:x -4.5999999940395355, :y 21.159999945163726} {:x -4.499999992549419, :y 20.249999932944775} {:x -4.399999991059303, :y 19.35999992132187} {:x -4.299999989569187, :y 18.48999991029501} {:x -4.199999988079071, :y 17.639999899864197} {:x -4.099999986588955, :y 16.80999989002943} {:x -3.999999985098839, :y 15.99999988079071} {:x -3.8999999836087227, :y 15.209999872148037} {:x -3.7999999821186066, :y 14.43999986410141} {:x -3.6999999806284904, :y 13.68999985665083} {:x -3.5999999791383743, :y 12.959999849796295} {:x -3.499999977648258, :y 12.249999843537807} {:x -3.399999976158142, :y 11.559999837875367} {:x -3.299999974668026, :y 10.889999832808972} {:x -3.19999997317791, :y 10.239999828338624} {:x -3.0999999716877937, :y 9.609999824464323} {:x -2.9999999701976776, :y 8.999999821186066} {:x -2.8999999687075615, :y 8.409999818503858} {:x -2.7999999672174454, :y 7.839999816417695} {:x -2.6999999657273293, :y 7.2899998149275795} {:x -2.599999964237213, :y 6.75999981403351} {:x -2.499999962747097, :y 6.249999813735487} {:x -2.399999961256981, :y 5.75999981403351} {:x -2.2999999597668648, :y 5.2899998149275795} {:x -2.1999999582767487, :y 4.839999816417696} {:x -2.0999999567866325, :y 4.4099998185038585} {:x -1.9999999552965164, :y 3.9999998211860675} {:x -1.8999999538064003, :y 3.609999824464323} {:x -1.7999999523162842, :y 3.2399998283386253} {:x -1.699999950826168, :y 2.8899998328089738} {:x -1.599999949336052, :y 2.5599998378753686} {:x -1.4999999478459358, :y 2.24999984353781} {:x -1.3999999463558197, :y 1.959999849796298} {:x -1.2999999448657036, :y 1.6899998566508323} {:x -1.1999999433755875, :y 1.4399998641014131} {:x -1.0999999418854713, :y 1.2099998721480403} {:x -0.9999999403953552, :y 0.999999880790714} {:x -0.8999999389052391, :y 0.8099998900294341} {:x -0.799999937415123, :y 0.6399998998642007} {:x -0.6999999359250069, :y 0.4899999102950137} {:x -0.5999999344348907, :y 0.3599999213218732} {:x -0.4999999329447746, :y 0.24999993294477912} {:x -0.3999999314546585, :y 0.1599999451637315} {:x -0.2999999299645424, :y 0.08999995797873034} {:x -0.19999992847442627, :y 0.039999971389775624} {:x -0.09999992698431015, :y 0.009999985396867361} {:x 7.450580596923828E-8, :y 5.551115123125783E-15} {:x 0.10000007599592209, :y 0.010000015199190193} {:x 0.2000000774860382, :y 0.04000003099442129} {:x 0.3000000789761543, :y 0.09000004738569883} {:x 0.40000008046627045, :y 0.16000006437302283} {:x 0.5000000819563866, :y 0.2500000819563933} {:x 0.6000000834465027, :y 0.3600001001358102} {:x 0.7000000849366188, :y 0.49000011891127354} {:x 0.8000000864267349, :y 0.6400001382827833} {:x 0.900000087916851, :y 0.8100001582503396} {:x 1.0000000894069672, :y 1.0000001788139423} {:x 1.1000000908970833, :y 1.2100001999735914} {:x 1.2000000923871994, :y 1.440000221729287} {:x 1.3000000938773155, :y 1.6900002440810291} {:x 1.4000000953674316, :y 1.9600002670288177} {:x 1.5000000968575478, :y 2.2500002905726526} {:x 1.6000000983476639, :y 2.560000314712534} {:x 1.70000009983778, :y 2.890000339448462} {:x 1.8000001013278961, :y 3.2400003647804363} {:x 1.9000001028180122, :y 3.610000390708457} {:x 2.0000001043081284, :y 4.000000417232524} {:x 2.1000001057982445, :y 4.410000444352638} {:x 2.2000001072883606, :y 4.840000472068798} {:x 2.3000001087784767, :y 5.290000500381005} {:x 2.400000110268593, :y 5.7600005292892575} {:x 2.500000111758709, :y 6.250000558793557} {:x 2.600000113248825, :y 6.760000588893903} {:x 2.700000114738941, :y 7.2900006195902955} {:x 2.8000001162290573, :y 7.840000650882734} {:x 2.9000001177191734, :y 8.41000068277122} {:x 3.0000001192092896, :y 9.000000715255752} {:x 3.1000001206994057, :y 9.61000074833633} {:x 3.200000122189522, :y 10.240000782012954} {:x 3.300000123679638, :y 10.890000816285626} {:x 3.400000125169754, :y 11.560000851154342} {:x 3.50000012665987, :y 12.250000886619107} {:x 3.6000001281499863, :y 12.960000922679917} {:x 3.7000001296401024, :y 13.690000959336775} {:x 3.8000001311302185, :y 14.440000996589678} {:x 3.9000001326203346, :y 15.210001034438628} {:x 4.000000134110451, :y 16.000001072883624} {:x 4.100000135600567, :y 16.810001111924667} {:x 4.200000137090683, :y 17.640001151561755} {:x 4.300000138580799, :y 18.490001191794892} {:x 4.400000140070915, :y 19.360001232624075} {:x 4.500000141561031, :y 20.250001274049303} {:x 4.6000001430511475, :y 21.160001316070577} {:x 4.700000144541264, :y 22.0900013586879} {:x 4.80000014603138, :y 23.040001401901268} {:x 4.900000147521496, :y 24.01000144571068})}], :width 400, :height 247.2188, :padding {:bottom 20, :top 10, :right 10, :left 50}}}"}
;; <=

;; **
;;; ##### Dene Simpson (@cljdeno)
;;; ##### 30 July 2014.
;; **
