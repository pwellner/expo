package abi45_0_0.host.exp.exponent.modules.api.reanimated.nodes;

import abi45_0_0.com.facebook.react.bridge.ReadableMap;
import abi45_0_0.host.exp.exponent.modules.api.reanimated.MapUtils;
import abi45_0_0.host.exp.exponent.modules.api.reanimated.NodesManager;

public abstract class ClockOpNode extends Node {

  public static class ClockStartNode extends ClockOpNode {
    public ClockStartNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
      super(nodeID, config, nodesManager);
    }

    @Override
    protected Double eval(Node clock) {
      if (clock instanceof ParamNode) {
        ((ParamNode) clock).start();
      } else {
        ((ClockNode) clock).start();
      }
      return ZERO;
    }
  }

  public static class ClockStopNode extends ClockOpNode {
    public ClockStopNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
      super(nodeID, config, nodesManager);
    }

    @Override
    protected Double eval(Node clock) {
      if (clock instanceof ParamNode) {
        ((ParamNode) clock).stop();
      } else {
        ((ClockNode) clock).stop();
      }
      return ZERO;
    }
  }

  public static class ClockTestNode extends ClockOpNode {
    public ClockTestNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
      super(nodeID, config, nodesManager);
    }

    @Override
    protected Double eval(Node clock) {
      if (clock instanceof ParamNode) {
        return ((ParamNode) clock).isRunning() ? 1. : 0.;
      }
      return ((ClockNode) clock).isRunning ? 1. : 0.;
    }
  }

  private int clockID;

  public ClockOpNode(int nodeID, ReadableMap config, NodesManager nodesManager) {
    super(nodeID, config, nodesManager);
    clockID =
        MapUtils.getInt(
            config,
            "clock",
            "Reanimated: Argument passed to clock node is either of wrong type or is missing.");
  }

  @Override
  protected Double evaluate() {
    Node clock = mNodesManager.findNodeById(clockID, Node.class);
    return eval(clock);
  }

  protected abstract Double eval(Node clock);
}
