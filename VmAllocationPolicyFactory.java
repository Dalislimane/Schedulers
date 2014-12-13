import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

	/**
	 * Binômes:
	 * DALISLIMANE Fatima
	 * ZEMMITI Mouna
	 * M2 RISM
	 */
	 
public class VmAllocationPolicyFactory extends VmAllocationPolicy {

	/** La table vm. */
	private Map<String, Host> vmTable;

	/**
	 * Crée le nouvel objet VmAllocationPolicyFactory.
	 * 
	 * @param liste
	 */
	public VmAllocationPolicyFactory(List<? extends Host> list) {
		super(list);

		setVmTable(new HashMap<String, Host>());
	}

	/**
	 * Alloue un hôte pour une VM donnée.
	 * 
	 * @param specification vm VM 
	 * @return true si l'hôte est alloué, false sinon
	 */
	
	public boolean allocateHostForVm(Vm vm) {

		boolean result = false;
		int tries = 0;

		if (!getVmTable().containsKey(vm.getUid())) { // si la vm est creer

				int moreFree = Integer.MIN_VALUE;
				int idx = -1;

				Host host = getHostList().get(idx);
				result = host.vmCreate(vm);

				if (result) { // si la vm est creer dans l'hôte
					getVmTable().put(vm.getUid(), host);
					result = true;
					break;
				} 
				}
				tries++;
		}

		return result;
	}

	/**
	 * Libère l'hôte utilisé par une machine virtuelle.
	 * 
	 * @param vm de vm
	 */
	
	public void deallocateHostForVm(Vm vm) {
		Host host = getVmTable().remove(vm.getUid());
		int idx = getHostList().indexOf(host);
		if (host != null) {
			host.vmDestroy(vm);
		}
	}

	/**
	 * Obtient l'hôte qui exécute le VM donnée appartenant à l'utilisateur donné.
	 * 
	 * @param vm de vm
	 * @return l'hôte avec le vmID and userID
	 */

	public Host getHost(Vm vm) {
		return getVmTable().get(vm.getUid());
	}

	/**
	 * @param vmId  
	 * @param userId 
	 * @return l'hôte avec le vmID and userID 
	 */ 
	
	public Host getHost(int vmId, int userId) { 
		return getVmTable().get(Vm.getUid(userId, vmId)); 
	}

	/**
	 * Gets de vm table.
	 * 
	 * @return la vm table
	 */
	public Map<String, Host> getVmTable() {
		return vmTable;
	}

	/**
	 * Sets de vm table.
	 * 
	 * @param vmTable la vm table
	 */
	protected void setVmTable(Map<String, Host> vmTable) {
		this.vmTable = vmTable;
	}


	public List<Map<String, Object>> optimizeAllocation(List<? extends Vm> vmList) {
		
		return null;
	}


	public boolean allocateHostForVm(Vm vm, Host host) {
		if (host.vmCreate(vm)) { // si la vm a été créer dans l'hôte
			getVmTable().put(vm.getUid(), host);

			int idx = getHostList().indexOf(host);

			Log.formatLine(
					"%.2f: VM #" + vm.getId() + " has been allocated to the host #" + host.getId(),
					CloudSim.clock());
			return true;
		}

		return false;
	}
