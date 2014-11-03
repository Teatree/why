package com.me.swampmonster.models.items;


public abstract class Modificator {
	
	public int probability;
	public String name;
	
	
	public abstract void applyModificator();
		
	public class EnemyBleedMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}
	public class StunEnemyMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}
	public class ExtraDamageToEnemyTypeMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}
	public class ExtraDamageToToughguyTypeMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}
	public class VampireMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}
	public class ExtraDamageMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}
	public class ShootThoughCollisionMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}
	public class DamagePlayerMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}
	public class HealEnemyMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}
	public class SpeedUpEnemyMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}
	public class StunPlayerMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}
	
	public class ShadowProjectileMod extends Modificator{
		
		@Override
		public void applyModificator() {
			System.out.println("mod: " + this.getClass().getSimpleName());
		}
		
	}

}
