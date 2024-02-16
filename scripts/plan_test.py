from Planner import planner as pl


if __name__ == "__main__":
    ple = pl.Planner()
    ple.read_topology_from_file("../config/demo/topology")
    ple.gen("../config/demo/reachability.puml", "D", ["S"], "(exist >= 1, S.*D)", fault_scenes=None)
    ple.gen("../config/demo/waypoint.puml", "D", ["S"], "(exist >= 1, S.*W.*D)", fault_scenes=None)
    ple.gen("../config/demo/limited_path.puml", "D", ["W"], "(exist >= 1, WD|W.D|W..D)", fault_scenes=None)
    ple.gen("../config/demo/different_ingress.puml", "D", ["A", "B"], "(exist >= 1, A.*D|B..D)", fault_scenes=None)
    ple.gen("../config/demo/all-shortest-path.puml", "D", ["S"], "(equal, (S.*D, (==shortest)))", fault_scenes=None)
    ple.gen("../config/demo/non-redundant.puml", "D", ["S"], "(exist == 1, S.*D)", fault_scenes=None)
